package ft.addon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JythonFunctionalityTest extends PythonAddon {

    /**
     * Construct transfer addon
     */
    public JythonFunctionalityTest(String script){
        registerModule("addon_test", script);
    }

    @Override
    public <T> T execute(Map<String, Object> parameters) {
        return execute("test", parameters);
    }

    @Override
    public <T> T execute(String func, Map<String, Object> parameters) {
        Map rt = executePython(Map.class, "addon_test", func, parameters);
        return (T)rt;
    }
}
