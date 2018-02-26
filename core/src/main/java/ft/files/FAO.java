package ft.files;

import java.io.InputStream;
import java.util.List;

/**
 * File access object
 */
interface FAO {
    /**
     * create a new file
     * @param path
     * @return
     */
    default boolean touch(String path) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * remove a file(not directory)
     * @param path
     * @return
     */
    default boolean rm(String path) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * create a new directory
     * @param path
     * @return
     */
    default boolean mkdir(String path) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * remove a directory
     * @param dir
     * @return
     */
    default boolean rmdir(String dir) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * rename given file to a new path
     * @param oldPath
     * @param newPath
     * @return
     */
    default boolean mv(String oldPath, String newPath) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * check if the specific file is exist
     * @param path
     * @return
     */
    default Boolean fileExist(String path) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * check if the specific directory is exist
     * @param path
     * @return
     */
    default Boolean dirExist(String path) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * list the files in the specific directory
     * @param dir
     * @return
     */
    default List<String> ls(String dir) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Read file content as bytes(high memory cost)
     * @param path
     * @return file content as bytes
     */
    default byte[] readAsBytes(String path) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Read file content as string(high memory cost)
     * @param path
     * @return file content as string
     */
    default String readAsString(String path) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * InputStream
     */
    interface ReadCallback {
        /**
         * @param in
         */
        void process(InputStream in);
    }

    /**
     * Read file as input stream
     * @param path
     * @return the input stream of file
     */
    default void readAsInputStream(String path, ReadCallback callback) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     *
     * @param path
     * @param content
     * @return
     */
    default boolean write(String path, byte[] content) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     *
     * @param path
     * @param text
     * @return
     */
    default boolean write(String path, String text) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     *
     * @param path
     * @param in
     * @return
     */
    default boolean write(String path, InputStream in) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
