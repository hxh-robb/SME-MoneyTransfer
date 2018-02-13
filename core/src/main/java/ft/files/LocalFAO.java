package ft.files;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * TODO:Local file access object
 */
@Repository @Qualifier("LocalFAO")
public class LocalFAO implements FAO{
    @Override
    public boolean create(String path, byte[] content) {
        return false;
    }

    @Override
    public boolean delete(String path) {
        return false;
    }

    @Override
    public boolean move(String oldPath, String newPath) {
        return false;
    }

    @Override
    public byte[] retrieve(String path) {
        return new byte[0];
    }
    // Apache common pool + ssh
}
