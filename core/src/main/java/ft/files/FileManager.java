package ft.files;

import ft.boot.DocPathConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * TODO:File manager
 */
@Component
public class FileManager {
    private DocPathConfiguration paths;
    private FAO remote;
    private FAO local;

    public FileManager(DocPathConfiguration paths, @Qualifier("RemoteFAO")FAO remote, @Qualifier("LocalFAO")FAO local) {
        this.paths = paths;
        this.remote = remote;
        this.local = local;
    }

    private String copy(String remotePath, String newParent){
        if(!isRaw(remotePath) || null == newParent){
            return null;
        }

        Boolean exist = remote.dirExist(newParent);
        if(null == exist) {
            return null;
        }
        if(!exist){
            if(!remote.mkdir(newParent)){
                return null;
            }
        }

        String filename = new File(remotePath).getName();
        String newPath = newParent.endsWith("/") ? newParent + filename : newParent + "/" + filename;
        exist = remote.fileExist(newPath);
        if( null == exist ) {
            return null;
        }
        if(exist || remote.cp(remotePath, newPath)){
            return newPath;
        }
        return null;
    }

    /**
     * Move remote files
     * @param remotePath
     * @return cert file path
     */
    public String copyTo(String remotePath, String tag, boolean returnWebURL){
        String parent = paths.getBase().endsWith("/") ? paths.getBase() + tag : paths.getBase() + "/" + tag;
        String newPath = copy(remotePath, parent);
        if( null == newPath ) {
            return null;
        }

        if(!returnWebURL) {
            return newPath;
        }
        String filename = new File(newPath).getName();
        return paths.getUrlBase().endsWith("/") ? paths.getUrlBase() + filename : paths.getUrlBase() + "/" + filename;
    }

    /**
     * Check if given value is a raw path
     * @param remotePath
     * @return
     */
    public boolean isRaw(String remotePath){
        if( null == remotePath ) {
            return false;
        }

        if(!remotePath.startsWith(paths.getRaw())){
            return false;
        }

        Boolean exist = remote.fileExist(remotePath);
        return null != exist && exist;
    }

    /*
    * 1) check if local has a copy
    * 2-a) if local has the copy, load it locally then return the file descriptor;
    * 2-b) if local doesn't have the copy, load it remotely (see section-3) ;
    *
    * 3)
    * */
}
