package ft.files;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * TODO:Virtual file system access object, using Apache Commons VFS as underlying file system manager
 */
@Repository @Qualifier("CommonFAO")
public class VfsFAO implements FAO {
}
