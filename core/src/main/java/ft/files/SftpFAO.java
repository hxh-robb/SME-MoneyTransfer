package ft.files;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import ft.ex.pool.ThreadLocalObjectPoolDecorator;
import org.apache.commons.pool2.ObjectPool;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO:SFTP file access object, using JSch + Apache Commons Pool2 as underlying file system manager
 */
@Repository @Qualifier("RemoteFAO")
class SftpFAO extends ThreadLocalObjectPoolDecorator.ThreadLocalObjectPoolHelper implements FAO{
    /**
     * Inject ChannelSftp pool
     * @param pool
     */
    public SftpFAO(ObjectPool<ChannelSftp> pool) {
        super(pool);
    }

    /**
     * Create file or directory
     * @param path
     * @param dir
     * @return
     */
    private boolean create(String path, boolean dir) {
        Boolean exist = exist(path, null);
        if( null == exist || exist ){
            return false;
        }

        ChannelSftp sftp = null;
        try {
            List<String> pathTree = Arrays.asList(path.split("/"));

            sftp = borrowObject();

            for (String d:pathTree) {
                if(path.startsWith("/") && "".equals(d)){
                    sftp.cd("/");
                    continue;
                }
                if(d == pathTree.get(pathTree.size() - 1)) {
                    break;
                }
                try {
                    sftp.cd(d);
                } catch (SftpException ex) {
                    sftp.mkdir(d);
                    sftp.cd(d);
                }
            }

            if(dir) {
                sftp.mkdir(pathTree.get(pathTree.size() - 1));
            } else
                sftp.put(new ByteArrayInputStream(new byte[]{}), pathTree.get(pathTree.size() - 1));
            return true;
        } catch (Throwable throwable) {
            return false;
        } finally {
            returnObject(sftp);
        }
    }

    @Override
    public boolean touch(String path) {
        return create(path, false);
    }

    @Override
    public boolean mkdir(String path) {
        return create(path, true);
    }

    /**
     * Delete file or directory
     * @param path
     * @param dir
     * @return
     */
    private boolean delete(String path, boolean dir) {
        Boolean exist = exist(path, null);
        if( null == exist || !exist ){
            return false;
        }

        ChannelSftp sftp = null;
        try {
            sftp = borrowObject();

            if(dir) {
                sftp.rmdir(path);
            } else
                sftp.rm(path);
            return true;
        } catch (Throwable throwable) {
            return false;
        } finally {
            returnObject(sftp);
        }
    }

    @Override
    public boolean rm(String path) {
        return delete(path, false);
    }

    @Override
    public boolean rmdir(String dir) {
        return delete(dir, true);
    }

    /**
     * Check if the given file/directory is exist or not
     * @param path
     * @param dir
     * @return
     */
    private Boolean exist(String path, Boolean dir) {
        ChannelSftp sftp = null;
        try {
            sftp = borrowObject();
            SftpATTRS attrs =  sftp.stat(path);

            if( null != dir && dir != attrs.isDir() )
                throw new Exception(String.format("File type is not match, [%s] is supposed to be a %s", path, dir ? "directory" : "file"));

            return true;
        } catch (SftpException noSuchFile) {
            return false; // JSch exceptions
        } catch (Throwable throwable) {
            LoggerFactory.getLogger(SftpFAO.class).error("Not sure if the file is exist or not",throwable);
            return null; // Apache Commons Pool2 and other exceptions
        } finally {
            returnObject(sftp);
        }
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
    public List<String> ls(String dir) {
        Boolean exist = dirExist(dir);
        if( null == exist || !exist ){
            return null;
        }

        ChannelSftp sftp = null;
        try {
            sftp = borrowObject();
            List<String> rt = new ArrayList<>();
            for (Object element : sftp.ls(dir)) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry)element;
                rt.add(dir + "/" + entry.getFilename());
            }
            return rt;
        } catch (Throwable throwable) {
            return null;
        } finally {
            returnObject(sftp);
        }
    }

    @Override
    public boolean mv(String oldPath, String newPath) {
        ChannelSftp sftp = null;
        try {
            sftp = borrowObject();
            sftp.rename(oldPath, newPath);
            return true;
        } catch (Throwable throwable) {
            return false;
        } finally {
            returnObject(sftp);
        }
    }

    @Override
    public boolean cp(String src, String dest) {
        ChannelSftp sftp = null;
        try {
            sftp = borrowObject();
            sftp.hardlink(src,dest);
            return true;
        } catch (Throwable throwable) {
            return false;
        } finally {
            returnObject(sftp);
        }
    }

    @Override
    public void readAsInputStream(String path, ReadCallback callback) {
        Boolean exist = fileExist(path);
        if( null == exist || !exist ){
            return;
        }

        ChannelSftp sftp = null;
        InputStream remoteIn = null;
        try {
            sftp = borrowObject();
            remoteIn = new BufferedInputStream(sftp.get(path));
            callback.process(remoteIn);
        } catch (Throwable throwable) {
            LoggerFactory.getLogger(SftpFAO.class).error("SFTP read error", throwable);
        } finally {
            if(null != remoteIn) {
                try { remoteIn.close(); } catch (Throwable throwable){}
            }
            returnObject(sftp);
        }
    }

    @Override
    public boolean write(String path, InputStream in) {
        Boolean exist = fileExist(path);
        if( null == exist || !exist ){
            return false;
        }

        ChannelSftp sftp = null;
        OutputStream remoteOut = null;
        try {
            sftp = borrowObject();
            remoteOut = sftp.put(path);
            byte[] buffer = new byte[1024];
            BufferedInputStream buffered = new BufferedInputStream(in);
            int n = 0;
            while( (n = buffered.read(buffer)) >= 0 ){
                remoteOut.write(buffer,0,n);
            }
            return true;
        } catch (Throwable throwable) {
            LoggerFactory.getLogger(SftpFAO.class).error("SFTP write error", throwable);
            return false;
        } finally {
            if( null != remoteOut){
                try { remoteOut.close(); } catch (Throwable th) {}
            }
            if( null != in){
                try { in.close(); } catch (Throwable th) {}
            }
            returnObject(sftp);
        }
    }
}
