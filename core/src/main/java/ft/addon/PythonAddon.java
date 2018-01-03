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
        } finally {
            for (String func:MODULE_FUNCTIONS.keySet()) {
                if(func.startsWith(module + "."))
                    MODULE_FUNCTIONS.remove(func);
            }
        }
    }

    /**
     * Execute python code
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
            pyFunc = PYTHON_INTERPRETER.eval(func);
            MODULE_FUNCTIONS.put(func,pyFunc);
        }
        return (T)pyFunc.__call__(Py.java2py(parameters)).__tojava__(returnType);
    }
}
