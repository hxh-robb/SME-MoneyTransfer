package ft.files;

import ft.boot.DocPathConfiguration;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * File manager
 */
@Component
public class FileManager {
    @Autowired
    private DocPathConfiguration paths;

    @Autowired @Qualifier("RemoteFAO")
    private FAO remote;

    @Autowired @Qualifier("LocalFAO")
    private FAO local;

    /**
     * copy file to new parent
     * @param remotePath
     * @param newParent
     * @return
     */
    private String remoteCopy(String remotePath, String newParent){
        if(!isRemoteRaw(remotePath) || null == newParent){
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
     * Copy remote files
     * @param remotePath
     * @return cert file path
     */
    public String remoteCopy(String remotePath, String tag, boolean returnWebURL){
        String parent = paths.getBase().endsWith("/") ? paths.getBase() + tag : paths.getBase() + "/" + tag;
        String newPath = remoteCopy(remotePath, parent);
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
    public boolean isRemoteRaw(String remotePath){
        if( null == remotePath ) {
            return false;
        }

        if(!remotePath.startsWith(paths.getRaw())){
            return false;
        }

        Boolean exist = remote.fileExist(remotePath);
        return null != exist && exist;
    }

    /**
     * Clone the remote file to local filesystem then return the local path
     * @param remotePath
     * @return
     */
    public String cloneAndGetLocalPath(String remotePath){
        if(null == remotePath) {
            return null;
        }

        Boolean exist = remote.fileExist(remotePath);
        if(null == exist || !exist) {
            return null;
        }

        String tmp = System.getProperty("java.io.tmpdir");
        String filename = "outwit-tmp-" + new File(remotePath).getName();
        File localFile = new File(tmp, filename);
        exist = local.fileExist(localFile.getPath());
        if( null == exist ) {
            return null;
        } else if (exist) {
            return localFile.getPath();
        }

        if(!local.touch(localFile.getPath())){
            return null;
        }

        final AtomicBoolean success = new AtomicBoolean(false);
        remote.readAsInputStream(remotePath, (inputStream)->{
            success.set(local.write(localFile.getPath(), inputStream));
        });

        if(success.get()) {
            return localFile.getPath();
        }
        return null;
    }

    public String sendRemoteRaw(String localPath){
        // Calculate file MD5
        Boolean exist = local.fileExist(localPath);
        if(null == exist || !exist){
            return null;
        }
        String filename;
        try(FileInputStream is = new FileInputStream(localPath)){
            filename = DigestUtils.md5Hex(is).toUpperCase();
        } catch(Throwable th){
            return null;
        }

        String remotePath = paths.getRaw() + filename;
        exist = remote.fileExist(remotePath);
        if(null == exist) {
            return null;
        }

        if(exist) {
            return remotePath;
        } else if(!remote.touch(remotePath)){
            return null;
        }

        final AtomicBoolean success = new AtomicBoolean(false);
        local.readAsInputStream(localPath, (localInput) -> {
            success.set(remote.write(remotePath, localInput));
        });

        if(success.get()) {
            return remotePath;
        }
        return null;
    }
}
