package ft.biz;

import ft.repo.MetadataDAO;
import ft.spec.model.Metadata;
import ft.spec.service.MetadataService;
import ft.spec.service.Result;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO
 */
public class MetadataBiz implements MetadataService {

    @Autowired
    private MetadataDAO dao;

    @Override
    public Metadata[] list(String subject, String catalog) {
        // TODO:log subject behaviors

        MetadataDAO.Filter filter = new MetadataDAO.Filter();
        filter.catalog = catalog;
        return dao.list(filter).toArray(new Metadata[]{});
    }

    @Override
    public Result create(String subject, Metadata metadata) {
        // TODO:log subject behaviors

        Result result = new Result();
        result.setCode(Result.Code.UNKNOWN);

        dao.create(metadata);
        result.setCode(Result.Code.SUCCESS);

        return result;
    }

    @Override
    public Result delete(String subject, String catalog, String name) {
        // TODO:log subject behaviors

        Result result = new Result();
        result.setCode(Result.Code.UNKNOWN);

        MetadataDAO.Filter filter = new MetadataDAO.Filter();
        filter.catalog = catalog;
        filter.name = name;
        dao.delete(filter);
        result.setCode(Result.Code.SUCCESS);

        return result;
    }
}
