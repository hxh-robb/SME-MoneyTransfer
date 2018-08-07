package com.sme.mts.test.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * DAO that logging operation error
 * @param <D>
 * @param <F>
 */
public abstract class LoggingDAO<D extends Data,F extends DataAccessObject.Filter> implements DataAccessObject<D,F> {
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public void create(D data) {
        try {
            doCreate(data);
        } catch (Throwable th) {
            logger.error("Create error");
            throw th;
        }
    }

    @Override
    public int update(F filter, D data) {
        try {
            return doUpdate(filter, data);
        } catch (Throwable th) {
            logger.error("Update error");
            throw th;
        }
    }

    @Override
    public int delete(F filter) {
        try {
            return doDelete(filter);
        } catch (Throwable th) {
            logger.error("Delete error");
            throw th;
        }
    }

    @Override
    public List<D> list(F filter) {
        try {
            return doList(filter);
        } catch (Throwable th) {
            logger.error("Create error");
            throw th;
        }
    }

    protected abstract void doCreate(D data);
    protected abstract int doUpdate(F filter, D data);
    protected abstract int doDelete(F filter);
    protected abstract List<D> doList(F filter);
}
