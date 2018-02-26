package ft.files;

import net.sf.cglib.core.Local;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO:Local file access object, using Java IO as file system manager
 */
@Repository @Qualifier("LocalFAO")
class LocalFAO implements FAO{

    /**
     * Create file or directory
     * @param path
     * @param dir
     * @return
     */
    private boolean create(String path, boolean dir) {
        try {
            File file = new File(path);

            if (!file.exists()) {
                return dir ? file.mkdirs() : file.createNewFile();
            }

            return dir ? file.isDirectory() : file.isFile();
        } catch (Throwable throwable) {
            return false;
        }
    }

    /**
     * Delete file or directory
     * @param path
     * @param dir
     * @return
     */
    private boolean delete(String path, boolean dir) {
        try {
            File file = new File(path);

            if (!file.exists()) {
                return false;
            }

            return file.delete();
        } catch (Throwable throwable) {
            return false;
        }
    }

    /**
     * Check if file or directory is exist
     * @param path
     * @param dir
     * @return
     */
    private Boolean exist(String path, boolean dir) {
        File file = new File(path);

        if(!file.exists()) {
            return false;
        }

        if(dir == file.isDirectory()){
            return true;
        }

        return null;
    }

    @Override
    public boolean touch(String path) {
        return create(path, false);
    }

    @Override
    public boolean mkdir(String path) {
        return create(path, true);
    }

    @Override
    public Boolean fileExist(String path) {
        return exist(path, false);
    }

    @Override
    public Boolean dirExist(String path) {
        return exist(path, true);
    }

    @Override
    public boolean rm(String path) {
        return delete(path, false);
    }

    @Override
    public boolean rmdir(String dir) {
        return delete(dir, true);
    }

    @Override
    public List<String> ls(String dir) {
        Boolean exist = dirExist(dir);
        if( null == exist || !exist ){
            return null;
        }

        File d = new File(dir);
        List<String> rt = new ArrayList<>();
        for(String path:d.list()){
            rt.add(dir + File.separator + path);
        }
        return rt;
    }

    @Override
    public boolean mv(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        if( !oldFile.exists() || newFile.exists())
            return false;

        return oldFile.renameTo(newFile);
    }

    @Override
    public void readAsInputStream(String path, ReadCallback callback) {
        Boolean exist = fileExist(path);
        if( null == exist || !exist ){
            return;
        }

        InputStream localIn = null;
        try {
            localIn = new BufferedInputStream(new FileInputStream(path));
            callback.process(localIn);
        } catch (Throwable throwable) {
            LoggerFactory.getLogger(LocalFAO.class).error("Local file read error", throwable);
        } finally {
            if(null != localIn) {
                try { localIn.close(); } catch (Throwable throwable){}
            }
        }

    }

    @Override
    public boolean write(String path, InputStream in) {
        Boolean exist = fileExist(path);
        if( null == exist || !exist ){
            return false;
        }

        OutputStream localOut = null;
        try {
            localOut = new FileOutputStream(path);
            byte[] buffer = new byte[1024];
            BufferedInputStream buffered = new BufferedInputStream(in);
            int n = 0;
            while( (n = buffered.read(buffer)) >= 0 ){
                localOut.write(buffer,0,n);
            }
            return true;
        } catch (Throwable throwable) {
            LoggerFactory.getLogger(LocalFAO.class).error("Local file write error", throwable);
            return false;
        } finally {

            if( null != localOut){
                try { localOut.close(); } catch (Throwable th) {}
            }
            if( null != in){
                try { in.close(); } catch (Throwable th) {}
            }
        }
    }
}
