package ft.files;

/**
 * File access object
 */
public interface FAO {
    /**
     * Create file by content
     * @param path
     * @param content
     */
    boolean create(String path, byte[] content);

    /**
     * Delete file
     * @param path
     */
    boolean delete(String path);

    /**
     * Rename file
     * @param oldPath
     * @param newPath
     * @return
     */
    boolean move(String oldPath, String newPath);

    /**
     * Retrieve file
     * @param path
     * @return
     */
    byte[] retrieve(String path);
}
