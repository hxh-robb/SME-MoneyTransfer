package ft.biz;

import ft.repo.DAO;
import ft.spec.model.Entity;
import ft.spec.service.Result;
import ft.spec.service.Service;

import java.util.Date;

public abstract class Biz<T extends Entity, D extends DAO> implements Service<T> {
    protected D dao;

    protected Biz(D dao) {
        this.dao = dao;
    }

    @Override
    public Result create(String subject, T entity) {
        // TODO:log subject behaviors

        Result result = new Result();

        if( dao.create(entity) )
            result.setCode(Result.Code.SUCCESS);

        return result;
    }

    public Result delete(String subject, String id) {
        // TODO:log subject behaviors

        Result result = new Result();
        if( null == id ) return result;

        DAO.Filter filter = createFilter(); // new MetadataDAO.Filter();
        filter.id = id;

        T changeDe = createEntity(); // new Metadata();
        changeDe.setId(null);
        changeDe.setDe(true);

        if( 1 == dao.update(filter, changeDe) )
            result.setCode(Result.Code.SUCCESS);

        return result;
    }

    @Override
    public Result update(String subject, T entity) {
        // TODO:log subject behaviors

        Result result = new Result();
        if( null == entity || null == entity.getId() ) return result;

        DAO.Filter filter = createFilter(); // new MetadataDAO.Filter();
        filter.id = entity.getId();
        entity.setTs(new Date());
        if( 1 == dao.update(filter, entity) )
            result.setCode(Result.Code.SUCCESS);

        return result;
    }

    protected abstract <F extends DAO.Filter> F createFilter();
    protected abstract <E extends T> E createEntity();
}
