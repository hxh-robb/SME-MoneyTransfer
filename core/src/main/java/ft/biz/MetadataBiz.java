package ft.biz;

import ft.repo.MetadataDAO;
import ft.spec.model.TransferAddon;
import ft.spec.model.Metadata;
import ft.spec.service.MetadataService;
import ft.spec.service.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * TODO
 */
@Service
public class MetadataBiz implements MetadataService {

    // @Autowired
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
    public Result create(String subject, Metadata metadata) {
        // TODO:log subject behaviors

        Result result = new Result();

        if( dao.create(metadata) )
            result.setCode(Result.Code.SUCCESS);

        return result;
    }

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

    @Override
    public List<TransferAddon> supportedTransferAddons(String subject) {
        MetadataDAO.TransferAddonFilter filter = new MetadataDAO.TransferAddonFilter();
        filter.de = false;
        return dao.list(filter);
    }
}
