package ft.addon;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Addon that capable to execute python code snippet
 */
public abstract class PythonAddon implements Addon {
    // Only one interpreter will exist for saving resource usage
    private static final PythonInterpreter PYTHON_INTERPRETER;
    private static final Map<String, PyObject> MODULE_FUNCTIONS;

    static {
        PYTHON_INTERPRETER = new PythonInterpreter();
        PYTHON_INTERPRETER.exec("import types"); // We need to use types.ModuleType

        MODULE_FUNCTIONS = new ConcurrentHashMap<>();
    }

    /**
     * Setup a addon-module variable
     * @param module
     * @param code
     */
    final static void registerModule(String module, String code){
        try {
            PYTHON_INTERPRETER.exec(MessageFormat.format("{0} = types.ModuleType('{0}')",module));
            PYTHON_INTERPRETER.exec(MessageFormat.format("exec ''''''\n{1}\n'''''' in {0}.__dict__", module, code));
        } catch (Throwable throwable) {
            // remove module
            PYTHON_INTERPRETER.exec(MessageFormat.format("globals().pop(''{0}'', None)",module));
            throw new IllegalArgumentException("Can not register module:" + module, throwable);
        } finally {
            // Remove all module function from cache
            for (String func:MODULE_FUNCTIONS.keySet()) {
                if(func.startsWith(module + "."))
                    MODULE_FUNCTIONS.remove(func);
            }
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
    protected <T> T executePython(Class<T> returnType, String module, String function, Map<String, Object> parameters) {
        String func = MessageFormat.format("{0}.{1}", module, function);
        PyObject pyFunc = MODULE_FUNCTIONS.get(func);
        if( null == pyFunc) {
            try {
                pyFunc = PYTHON_INTERPRETER.eval(func); // get python function object
            } catch (Throwable throwable) {
                throw new UnsupportedOperationException("Python function(" + func + ") is undefined");
            }
            if( null == pyFunc)
                throw new UnsupportedOperationException("Python function(" + func + ") does not exist");
            MODULE_FUNCTIONS.put(func,pyFunc); // cache the function
        }

        /*
        PyObject.__call__ is 30 times slower than java code,but 100 times faster than PythonInterpreter.eval
        Besides, PythonInterpreter is not thread-safe, so that we use PyObject.__call__ as an alternatives
         */
        return (T)pyFunc.__call__(Py.java2py(parameters)).__tojava__(returnType);
    }
}
