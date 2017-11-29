package ft.repo;

import java.util.List;

/**
 * DAO base interface
 * @param <T> data type
 * @param <F> filter type
 */
public interface DAO<T,F> {
    /**
     * Create data
     * @param data
     */
    void create(T data);

    /**
     * Update data
     * @param data
     */
    void update(T data);

    /**
     * Delete data
     * @param id
     */
    void delete(Object id);

    /**
     * List data
     * @param filter
     * @return
     */
    List<T> list(F filter);
}
