package ft.biz;

import ft.repo.MetadataDAO;
import ft.spec.model.Metadata;
import ft.spec.model.TransferAddon;
import ft.spec.service.MetadataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Metadata service implementation
 */
@Service
public class MetadataBiz extends EntityBiz<Metadata, MetadataDAO> implements MetadataService {
/*

    @Autowired
    private MetadataDAO dao;

//    @Override
//    public List<Metadata> list(String subject, String catalog) {
//        // TODO:log subject behaviors
//
//        MetadataDAO.Filter filter = new MetadataDAO.Filter();
//        filter.catalog = catalog;
//        return dao.list(filter);
//    }

    @Override
    public Result delete(String subject, String id) {
        // TODO:log subject behaviors

        Result result = new Result();
        if( null == id ) return result;

        MetadataDAO.Filter filter = new MetadataDAO.Filter();
        filter.id = id;

        Metadata changeDe = new Metadata();
        changeDe.setId(null);
        changeDe.setDe(true);

        if( 1 == dao.update(filter, changeDe) )
            result.setCode(Result.Code.SUCCESS);

        return result;
    }

    @Override
    public Result update(String subject, Metadata metadata){
        // TODO:log subject behaviors

        Result result = new Result();
        if( null == metadata || null == metadata.getId() ) return result;

        MetadataDAO.Filter filter = new MetadataDAO.Filter();
        filter.id = metadata.getId();
        metadata.setTs(new Date());
        if( 1 == dao.update(filter, metadata) )
            result.setCode(Result.Code.SUCCESS);

        return result;
    }
*/
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

    @Override
    protected MetadataDAO.Filter createFilter() {
        return new MetadataDAO.Filter();
    }

    @Override
    protected Metadata createEntity() {
        return new Metadata();
    }
}
