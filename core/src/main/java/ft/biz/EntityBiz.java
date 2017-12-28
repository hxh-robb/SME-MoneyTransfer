package ft.biz;

import ft.repo.DAO;
import ft.spec.model.Entity;
import ft.spec.service.EntityService;
import ft.spec.service.Result;

import java.util.Date;

/**
 * Base entity service implementation
 * @param <T> Entity Type
 * @param <D> DAO Type
 */
public abstract class EntityBiz<T extends Entity, D extends DAO> extends Biz implements EntityService<T> {
    protected D dao;

    protected EntityBiz(D dao) {
        this.dao = dao;
    }

    @Override
    public Result create(String subject, T entity) {
        processSubject(subject);

        Result result = new Result();
        if( invalid(entity) ) return result.setCode(Result.Code.INVALID_ENTITY); // Given entity is invalid
        if( duplicated(entity) ) return result.setCode(Result.Code.DUPLICATED_ENTITY); // Given entity is duplicated

        if( dao.create(entity) )
            result.setCode(Result.Code.SUCCESS);

        return result;
    }

    @Override
    public Result delete(String subject, String id) {
        processSubject(subject);

        Result result = new Result();
        if( null == id ) return result.setCode(Result.Code.INVALID_PARAMETERS);

        DAO.Filter filter = createFilter();
        filter.id = id;

        T changeDe = createEntity();
        changeDe.setId(null);
        changeDe.setDe(true);

        if( 1 == dao.update(filter, changeDe) ) // Soft delete
            result.setCode(Result.Code.SUCCESS);

        return result;
    }

    @Override
    public Result update(String subject, T entity) {
        processSubject(subject);

        Result result = new Result();
        if( invalid(entity) ) return result.setCode(Result.Code.INVALID_ENTITY);

        DAO.Filter filter = createFilter();
        filter.id = entity.getId();
        entity.setTs(new Date());
        if( 1 == dao.update(filter, entity) )
            result.setCode(Result.Code.SUCCESS);

        return result;
    }

    /**
     * Check if the properties of the given entity are invalid
     * @param entity
     * @return
     */
    protected boolean invalid(T entity){
        return null == entity || null == entity.getId();
    }

    /**
     * Check if the given entity is duplicated data
     * @param entity
     * @return
     */
    protected boolean duplicated(T entity){
        return false; // Extended by sub-class
    }

    protected abstract <F extends DAO.Filter> F createFilter();
    protected abstract <E extends T> E createEntity();
}
