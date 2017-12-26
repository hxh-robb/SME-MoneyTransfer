package ft.repo;

import ft.spec.model.Entity;

import java.util.List;

/**
 * DAO base interface
 * @param <T> data type
 * @param <F> filter type
 */
public interface DAO<T extends Entity, F extends DAO.Filter> {
    /**
     * Create data
     * @param data
     * @return success or not
     */
    boolean create(T data);

    /**
     * Update data
     * @param filter
     * @param data
     * @return  -1 = error, otherwise the update count
     */
    int update(F filter, T data);

    /**
     * Delete data
     * @param filter
     * @return  -1 = error, otherwise the update count
     */
    int delete(F filter);

    /**
     * List data
     * @param filter
     * @return
     */
    <E extends T> List<E> list(F filter);

    /**
     * Base filter class
     */
    class Filter {
        public String id;
        public Boolean de;
    }
}
