package ft.ex.pool;

import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.pool2.ObjectPool;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ThreadLocal ObjectPool decorator,to make pooled resource be shared across the methods which invokes by the same thread.
 * @param <T>
 */
public class ThreadLocalObjectPoolDecorator<T> extends ObjectPoolDecorator<T> {
    public ThreadLocalObjectPoolDecorator(ObjectPool<T> pool) {
        super(pool);
    }

    private ThreadLocal<T> holder = new ThreadLocal<>();

    @Override
    public T borrowObject() throws Exception {
        T resource = holder.get();
        if( null == resource ) {
            resource = super.borrowObject();
            holder.set(resource);
        }
        return resource;
    }

    @Override
    public void returnObject(T obj) throws Exception {
        T resource = holder.get();
        if( null != resource && obj == resource ) {
            holder.set(null);
            super.returnObject(obj);
        }
    }

    /**
     * The following code is just my Interview-Oriented development in ThreadLocal + multi-threads + Apache Commons Pool2 coordination;
     * <pre>
     * If you're going to read the following code and need to have a teamwork with Flynn and Single in this project as I did,
     * then I feel terribly sorry for you, the pal who will takeover my job. Cause you'll soon find out how painful will be to work in the team led by both Flynn and Single.
     *
     * Flynn, the guy who will make schedule and promise to the boss, is always pretending he knew something about programming and technologies which he absolutely doesn't.
     * The result is that he will plan a ridiculous unrealistic development task for the team and drive the project into a dead end.
     *
     * Single, who is supposed to be a senior lead programmer, is always trying to solve problems in an unnecessary complex way, but he will never solve the most important one.
     * Also, he'd like to bring the team into a mess so that he can buy time for himself, which makes the team poor productivity. He is a speecher other than a practitioner.
     *
     * So my suggestion to you is that prepare your next interview ASAP, try to do some code practice that useful to the next interview in this project.
     * And I hope you can find an excited job other than this one soon.
     * </pre>
     */
    public static class ThreadLocalObjectPoolHelper {
        // Low-level SFTP file management
        private ObjectPool<ChannelSftp> pool;
        private Map<ChannelSftp, Thread> map;

        protected ThreadLocalObjectPoolHelper(ObjectPool<ChannelSftp> pool) {
            this.pool = pool;
            this.map = new ConcurrentHashMap<>();
        }

        /**
         * Borrow ChannelSftp instance from pool
         * @return
         * @throws Throwable
         */
        protected ChannelSftp borrowObject() throws Throwable {
            return borrowObject(false);
        }

        /**
         * Borrow ChannelSftp instance from pool
         * @param askForNew false for reuse the thread-local ChannelSftp instance
         * @return
         * @throws Throwable
         */
        protected ChannelSftp borrowObject(boolean askForNew) throws Throwable {
            try {
                if(pool instanceof ThreadLocalObjectPoolDecorator && askForNew) {
                    AtomicReference<ChannelSftp> ref = new AtomicReference<>();
                    AtomicReference<Throwable> err = new AtomicReference<>();
                    Thread t = new Thread(() -> {
                        synchronized (pool) {
                            try {
                                if(!ref.compareAndSet(null, pool.borrowObject())) {
                                    throw new Exception("Impossible situation!");
                                }
                            } catch (Throwable th) {
                                LoggerFactory.getLogger(ThreadLocalObjectPoolHelper.class).error("ChannelSftp Leak! Can Not return it to pool!", th);
                                ref.set(null);
                                err.set(th);
                                return;
                            } finally {
                                if( null != ref.get() ){
                                    map.put(ref.get(), Thread.currentThread());
                                }
                                pool.notify();
                            }

                            try {
                                pool.wait(); // Wait for returnObject
                                pool.returnObject(ref.get());
                            } catch (Throwable th) {
                                LoggerFactory.getLogger(ThreadLocalObjectPoolHelper.class).error("ChannelSftp Leak! Can Not return it to pool!", th);
                            } finally {
                                map.remove(ref.get());
                            }
                        }
                    });

                    synchronized (pool) {
                        t.start();
                        pool.wait();
                    }

                    if( null != err.get() ) {
                        throw err.get();
                    }

                    return ref.get();
                }

                return pool.borrowObject();
            } catch (Throwable th) {
                LoggerFactory.getLogger(ThreadLocalObjectPoolHelper.class).error("Can NOT borrow ChannelSftp instance from pool", th);
                throw th;
            }
        }

        /**
         * Return the ChannelSftp instance back to pool
         * @param sftp
         */
        protected void returnObject(ChannelSftp sftp){
            if( null != sftp ) {
                try {
                    if(pool instanceof ThreadLocalObjectPoolDecorator && map.containsKey(sftp)){
                        Thread t = map.remove(sftp);
                        synchronized (pool) {
                            pool.notify();
                        }
                        if( null != t ) {
                            t.join();
                        }
                        return;
                    }

                    pool.returnObject(sftp);
                } catch (Throwable th) {
                    LoggerFactory.getLogger(ThreadLocalObjectPoolHelper.class).error("Can NOT return ChannelSftp instance to pool", th);
                }
            }
        }
    }
}
