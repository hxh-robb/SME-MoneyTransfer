package ft.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * TODO:File manager
 */
@Component
public class FileManager {
    @Autowired @Qualifier("RemoteFAO")
    private FAO remote;

    @Autowired @Qualifier("LocalFAO")
    private FAO local;

    /*
    * input: remote path of raw file
    * output: remote path of organized file
    * */



    /*
    * 1) check if local has a copy
    * 2-a) if local has the copy, load it locally then return the file descriptor;
    * 2-b) if local doesn't have the copy, load it remotely (see section-3) ;
    *
    * 3)
    * */
}
