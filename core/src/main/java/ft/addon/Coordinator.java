package ft.addon;

import ft.files.FileManager;
import ft.repo.MetadataDAO;
import ft.spec.model.Metadata;
import ft.spec.model.TransferAddon;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Helper that performs addon execution
 */
@Component
public class Coordinator {
    private final Map<String, Addon> ADDONS = new ConcurrentHashMap<>();
    private final Map<String, ReadWriteLock> LOCKS = new ConcurrentHashMap<>();

    private MetadataDAO dao;
    private TemplateEngine tp;

    /**
     * Constructor injection
     * @param dao
     * @param tp
     */
    public Coordinator(MetadataDAO dao, TemplateEngine tp) {
        this.dao = dao;
        this.tp = tp;
    }

    /**
     * Cast metadata to addon
     * @param metadata
     * @return
     */
    private Addon cast(Metadata metadata) {
        try {
            if( null == metadata )
                return null;

            if( metadata instanceof TransferAddon ) {
                TransferAddon ta = (TransferAddon)metadata;

                if( TransferAddon.Type.JAVA.equals(ta.getType()) ) {
                    Class clazz = Class.forName(ta.getContent());
                    Object obj = clazz.newInstance();
                    if(obj instanceof Addon)
                        return (Addon)obj;
                }

                if ( TransferAddon.Type.PYTHON.equals(ta.getType()) ) {
                    // Intermediary deposit helper
                    if( TransferAddon.Mode.INTERMEDIARY_DEPOSIT.equals(ta.getMode()) ) {
                        return new IntermediaryDepositHelper(ta, tp);
                    }
                }
            }/* else if {
                // TODO:other type addon
            }*/

            return null;
        } catch(Throwable throwable) {
            LoggerFactory.getLogger(this.getClass()).error("Create addon fail", throwable);
            return null;
        }
    }

    /**
     * Get lock
     * @param metadataId
     * @param init
     * @return
     */
    private final ReadWriteLock getLock(String metadataId, boolean init) {
        ReadWriteLock lock = LOCKS.get(metadataId);
        if( init && null == lock ) {
            synchronized (LOCKS) {
                if(null == (lock = LOCKS.get(metadataId))) {
                    lock = new ReentrantReadWriteLock();
                    LOCKS.put(metadataId, lock);
                }
            }
        }
        return lock;
    }

    @Autowired
    private FileManager fm;

    @EventListener @SuppressWarnings("unused")
    public void refreshTransferAddon(Metadata metadata){
        if( null == metadata || null == metadata.getId() || !(metadata instanceof TransferAddon) ){
            return;
        }

        ReadWriteLock lock = getLock(metadata.getId(), true);
        lock.writeLock().lock();
        try {
            if (metadata.getDe()) {
                Addon addon = ADDONS.remove(metadata.getId());
                if(null != addon){
                    addon.deactive();
                }
                synchronized (LOCKS) {
                    LOCKS.remove(metadata.getId());
                }
            } else {
                Addon addon = cast(metadata);
                if (null != addon) {
                    ADDONS.put(metadata.getId(), addon);
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @EventListener @SuppressWarnings("unused")
    public void loadAllTransferAddons(ApplicationReadyEvent event){
        PythonAddon.setFileManager(fm);

        MetadataDAO.Filter filter = new MetadataDAO.Filter();
        filter.de = false;
        List<Metadata> list = dao.list(filter);
        for (Metadata metadata: dao.list(filter)) {
            refreshTransferAddon(metadata);
        }
    }

    /**
     * Execute addon
     * @param metadataId
     * @param parameters
     * @param <T>
     * @return
     */
    public <T> T execute(String metadataId,Map<String,Object> parameters) {
        return execute(metadataId, null, parameters);
    }

    /**
     * Execute addon
     * @param metadataId
     * @param function
     * @param parameters
     * @param <T>
     * @return
     */
    public <T> T execute(String metadataId, String function, Map<String,Object> parameters) {
        ReadWriteLock lock = getLock(metadataId, false);
        if(null == lock){
            LoggerFactory.getLogger(this.getClass()).error("Can not find addon lock:" + metadataId);
            return null;
        }

        lock.readLock().lock();
        try {
            Addon addon = ADDONS.get(metadataId); // lookup(metadataId);
            if (null == addon) {
                LoggerFactory.getLogger(this.getClass()).error("Can not find available addon:" + metadataId);
                return null;
            }

            if (null == function)
                return addon.execute(parameters);

            return addon.execute(function, parameters);
        } finally {
            lock.readLock().unlock();
        }
    }
}
