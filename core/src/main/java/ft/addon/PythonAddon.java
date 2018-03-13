package ft.addon;

import ft.files.FileManager;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Addon that capable to execute python code snippet
 */
abstract class PythonAddon implements Addon {
    // For saving resource usage, just keep one interpreter
    private static final PythonInterpreter PYTHON_INTERPRETER;

    //
    private static final Map<String, PyObject> MODULE_FUNCTIONS;
    private static final Map<String, ReadWriteLock> MODULE_LOCKS;

    static {
        PYTHON_INTERPRETER = new PythonInterpreter(); // high-overhead operation
        PYTHON_INTERPRETER.exec("import types"); // We need to use types.ModuleType

        MODULE_FUNCTIONS = new ConcurrentHashMap<>();
        MODULE_LOCKS = new ConcurrentHashMap<>();
    }

    public static final void setFileManager(FileManager fm) {
        SSH.fm = fm;
    }

    private static void removeModuleFunctions(String module){
        // Remove all module functions from cache
        for (String func:MODULE_FUNCTIONS.keySet()) {
            if(func.startsWith(module + "."))
                MODULE_FUNCTIONS.remove(func);
        }
    }

    /**
     * Get lock
     * @param module
     * @return
     */
    private static final ReadWriteLock getModuleLock(String module, boolean init) {
        ReadWriteLock lock = MODULE_LOCKS.get(module);
        if( init && null == lock ) {
            synchronized (MODULE_LOCKS) {
                if(null == (lock = MODULE_LOCKS.get(module))) {
                    lock = new ReentrantReadWriteLock();
                    MODULE_LOCKS.put(module, lock);
                }
            }
        }
        return lock;
    }

    /**
     * Setup a addon-module variable
     * @param module
     * @param code
     */
    final static void registerModule(String module, String code){
        ReadWriteLock lock = getModuleLock(module, true);

        lock.writeLock().lock();
        try {
            deregisterModule(module, false);
            PYTHON_INTERPRETER.exec(MessageFormat.format("{0} = types.ModuleType(''{0}'')", module));
            PYTHON_INTERPRETER.exec(MessageFormat.format("exec ''''''\n{1}\n'''''' in {0}.__dict__", module, code));
        } catch (Throwable throwable) {
            // remove module
            deregisterModule(module,true);
            throw new IllegalArgumentException("Can not register module:" + module, throwable);
        } finally {
            // Remove all module function from cache
            removeModuleFunctions(module);
            lock.writeLock().unlock();
        }
    }

    /**
     * Remove specific addon-module variable
     * @param module
     */
    final static void deregisterModule(String module, boolean removeLock) {
        ReadWriteLock lock = getModuleLock(module, false);
        if(null == lock){
            return;
        }

        lock.writeLock().lock();
        try {
            PYTHON_INTERPRETER.exec(MessageFormat.format("if ''{0}'' in globals(): globals().pop(''{0}'', None)",module));
        } catch (Throwable throwable) {
            throw new IllegalArgumentException("Can not remove module:" + module, throwable);
        } finally {
            // Remove all module function from cache
            removeModuleFunctions(module);
            if(removeLock){
                synchronized (MODULE_LOCKS) {
                    MODULE_LOCKS.remove(module);
                }
            }
            lock.writeLock().unlock();
        }
    }

    /**
     * Call python code
     * @param returnType
     * @param module
     * @param function
     * @param parameters
     * @param <T>
     * @return
     */
    protected <T> T executePython(Class<T> returnType, String module, String function, Object parameters) {
        ReadWriteLock lock = getModuleLock(module, false);
        if(null == lock){
            throw new UnsupportedOperationException("Python module(" + module + ") is undefined");
        }

        lock.readLock().lock();
        try {
            String func = MessageFormat.format("{0}.{1}", module, function);
            PyObject pyFunc = MODULE_FUNCTIONS.get(func);
            if (null == pyFunc) {
                try {
                    pyFunc = PYTHON_INTERPRETER.eval(func); // get python function object
                } catch (Throwable throwable) {
                    throw new UnsupportedOperationException("Python function(" + func + ") is undefined");
                }
                if (null == pyFunc)
                    throw new UnsupportedOperationException("Python function(" + func + ") does not exist");
                MODULE_FUNCTIONS.put(func, pyFunc); // cache the function
            }


            /*
            PyObject.__call__ is 30 times slower than java code,but 100 times faster than PythonInterpreter.eval
            Besides, PythonInterpreter is not thread-safe, so that we use PyObject.__call__ as an alternative
             */
            return (T) pyFunc.__call__(Py.java2py(parameters)).__tojava__(returnType);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Secure shell utility class for python addon;
     */
    public static final class SSH {
        private static FileManager fm;

        public static final boolean isReady(){
            return fm != null;
        }

        public static final String remoteCopy(String remotePath, String tag, boolean returnWebURL) {
            return fm.remoteCopy(remotePath, tag, returnWebURL);
        }

        public static final boolean isRemoteRaw(String remotePath) {
            return fm.isRemoteRaw(remotePath);
        }

        public static final String cloneAndGetLocalPath(String remotePath) {
            return fm.cloneAndGetLocalPath(remotePath);
        }

        public static final String sendRemoteRaw(String localPath) {
            return fm.sendRemoteRaw(localPath);
        }
    }
}
