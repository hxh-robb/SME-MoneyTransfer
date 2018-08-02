package com.sme.mts.data;

import java.util.List;

/**
 * Data access object base interface
 * @param <D> data type
 * @param <F> filter type
 */
public interface DataAccessObject<D extends Data, F extends DataAccessObject.Filter> {
    /**
     * Create data
     * @param data
     * @return success or not
     */
    boolean create(D data);

    /**
     * Update data
     * @param filter
     * @param data
     * @return  -1 = error, otherwise the update count
     */
    int update(F filter, D data);

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
    List<D> list(F filter);

    /**
     * Base filter class
     */
    class Filter {
        public String id;
        public Boolean de;
    }
}