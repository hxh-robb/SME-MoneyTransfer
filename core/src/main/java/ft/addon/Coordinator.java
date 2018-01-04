package ft.addon;

import ft.repo.MetadataDAO;
import ft.spec.model.Metadata;
import ft.spec.model.TransferAddon;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Helper that performs addon execution
 */
@Component
public class Coordinator {
    /**
     * Addon context:
     * key = metadata id;
     * value = addon instance;
     */
    private final Map<String, Addon> CONTEXT = new ConcurrentHashMap<>();

    private MetadataDAO dao;

    /**
     * Constructor injection
     * @param dao
     */
    public Coordinator(MetadataDAO dao) {
        this.dao = dao;
    }

//    private void reset(){
//        // clean addon context to release memory
//        CONTEXT.clear();
//
//        // clean variables which name started with "addon_" (Not sure if memory will be released or not)
//        PYTHON_INTERPRETER.exec("for key in [key for key in globals() if key.startswith('addon_')]: globals().pop(key)");
//    }

//    /**
//     * Initialize context
//     */
//    private void init() {
//        // Load addon
//    }

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
        Addon addon = lookup(metadataId);
        if( null == addon ) {
            LoggerFactory.getLogger(this.getClass()).error("Can not find available addon:" + metadataId);
            return null;
        }

        if( null == function )
            return addon.execute(parameters);

        return addon.execute(function, parameters);
    }

    /**
     * Lookup addon from context,
     * if context doesn't contain specific addon, query the addon details from database then create a new addon instance
     * @param metadataId
     * @return
     */
    private Addon lookup(String metadataId) {
        Addon addon = CONTEXT.get(metadataId);
        if( null == addon ) {
            MetadataDAO.Filter filter = new MetadataDAO.Filter();
            filter.id = metadataId;
            filter.de = false;
            for (Metadata metadata: dao.list(filter)) {
                addon = cast(metadata);
                if( null != addon ) {
                    CONTEXT.put(metadataId, addon);
                    break;
                }
            }
        }
        return addon;
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
                        return new IntermediaryDepositHelper(ta);
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
}
