package ft.ex.pool;

import org.apache.commons.pool2.ObjectPool;

import java.util.NoSuchElementException;

/**
 * Base ObjectPool decorator
 * @param <T>
 */
public abstract class ObjectPoolDecorator<T> implements ObjectPool<T> {
    protected final ObjectPool<T> underlying;
    public ObjectPoolDecorator(ObjectPool<T> pool) {
        this.underlying = pool;
    }

    @Override
    public T borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
        return underlying.borrowObject();
    }

    @Override
    public void returnObject(T obj) throws Exception {
        underlying.returnObject(obj);
    }

    @Override
    public void invalidateObject(T obj) throws Exception {
        underlying.invalidateObject(obj);
    }

    @Override
    public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {
        underlying.addObject();
    }

    @Override
    public int getNumIdle() {
        return underlying.getNumIdle();
    }

    @Override
    public int getNumActive() {
        return underlying.getNumActive();
    }

    @Override
    public void clear() throws Exception, UnsupportedOperationException {
        underlying.clear();
    }

    @Override
    public void close() {
        underlying.close();
    }
}
