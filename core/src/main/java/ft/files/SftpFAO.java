package ft.files;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * TODO:SFTP file access object
 */
@Repository @Qualifier("RemoteFAO")
public class SftpFAO {
    // Apache common pool + ssh
}
