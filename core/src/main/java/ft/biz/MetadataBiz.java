package ft.biz;

import ft.repo.MetadataDAO;
import ft.spec.model.Metadata;
import ft.spec.model.TransferAddon;
import ft.spec.service.MetadataService;
import ft.spec.service.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Metadata service implementation
 */
@Service @Qualifier("ActualMetadataService")
public class MetadataBiz extends EntityBiz<Metadata, MetadataDAO> implements MetadataService {
    public MetadataBiz(MetadataDAO dao) {
        super(dao); // Constructor injection
    }

    @Override
    protected boolean invalid(Metadata metadata) {
        if( super.invalid(metadata) ) return true;
        return null == metadata.getCatalog() || null == metadata.getValue();
    }

    @Override
    protected boolean duplicated(Metadata metadata) {
        MetadataDAO.Filter filter = new MetadataDAO.Filter();
        filter.catalog = metadata.getCatalog();
        filter.value = metadata.getValue();
        filter.de = false;
        return !dao.list(filter).isEmpty();
    }

    @Override
    public List<TransferAddon> supportedTransferAddons(String subject) {
        processSubject(subject);

        MetadataDAO.TransferAddonFilter filter = new MetadataDAO.TransferAddonFilter();
        filter.de = false;
        return dao.list(filter);
    }

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public Result create(String subject, Metadata entity) {
        Result result = super.create(subject, entity);
        if(Code.SUCCESS == result.getCode()) {
            publisher.publishEvent(entity);
        }
        return result;
    }

    @Override
    public Result delete(String subject, String id) {
        Result result = super.delete(subject, id);
        if(Code.SUCCESS == result.getCode()) {
            TransferAddon deleted = new TransferAddon();
            deleted.setId(id);
            deleted.setDe(true);
            publisher.publishEvent(deleted);
        }
        return result;
    }

    @Override
    public Result update(String subject, Metadata entity) {
        Result result = super.update(subject, entity);
        if(Code.SUCCESS == result.getCode()) {
            publisher.publishEvent(entity);
        }
        return result;
    }

    @Override
    protected MetadataDAO.Filter createFilter() {
        return new MetadataDAO.Filter();
    }

    @Override
    protected Metadata createEntity() {
        return new Metadata();
    }
}
