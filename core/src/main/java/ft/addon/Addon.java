package ft.addon;

import java.util.Map;

/**
 * Actual addon
 */
public interface Addon {
//    /**
//     * @param input
//     * @return output
//     */
//    <T> T execute(Object ... input);
//
//    /**
//     * @param func
//     * @param input
//     * @param <T>
//     * @return
//     */
//    <T> T execute(String func, Object ... input);

    /**
     * @param parameters
     * @return
     */
    <T> T execute(Map<String, Object> parameters);

    /**
     * @param func
     * @param parameters
     * @return
     */
    <T> T execute(String func, Map<String, Object> parameters);

    /**
     * Deactive this addon
     */
    default void deactive(){};
}
