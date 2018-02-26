package ft.ex.pool;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import ft.boot.SftpConfiguration;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.stereotype.Component;

/**
 * Core custom component for Apache Commons Pool2
 */
@Component
public class ChannelSftpFactory extends BasePooledObjectFactory<ChannelSftp> {

    private SftpConfiguration configuration;
    public ChannelSftpFactory(SftpConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Use one session with multiple channels mechanism for parallel processing.
     * Max number limit of opened channels is depending on server side sshd MaxSessions option
     */
    private volatile Session session;

    /**
     * Get session from cache
     * @return
     * @throws JSchException
     */
    private Session getSession() throws JSchException {
        if( null != session ) {
            return session;
        }

        synchronized (ChannelSftpFactory.class) {
            if( null != session ) {
                return session;
            }

            Session s = null;
            try {
                s = new JSch().getSession(configuration.getUsername(), configuration.getHost(), configuration.getPort());
                s.setPassword(configuration.getPassword());
                s.setConfig("StrictHostKeyChecking", "no");
                s.connect(configuration.getConnectTimeout());
                return s;
            } catch (Exception ex) {
                s = null;
                throw ex;
            } finally {
                session = s;
            }
        }
    }

    /**
     * Reset broken session
     * @param broken
     */
    private void resetSession(Session broken){
        if( null == broken )
            return;

        synchronized (ChannelSftpFactory.class) {
            if( broken == session ) {
                session = null;
            }
        }
    }

    /**
     * Create ChannelSftp instance
     * @return
     * @throws Exception
     */
    @Override
    public ChannelSftp create() throws Exception {
        Session session = null;
        ChannelSftp sftp = null;

        try {
            session = getSession();
        } catch(Exception ex) {
            resetSession(session);
            throw ex;
        }

        try {
            sftp = (ChannelSftp)session.openChannel("sftp");
            sftp.connect(configuration.getConnectTimeout());
        } catch(Exception ex) {
            if( null != sftp ) {
                sftp.disconnect();
            }
            throw ex;
        }

        return sftp;
    }

    /**
     * Wrap ChannelSftp instance into PooledObject
     * @param sftp
     * @return
     */
    @Override
    public PooledObject<ChannelSftp> wrap(ChannelSftp sftp) {
        // ChannelSftp is not heavy memory-overhead resource, so just use strong reference version of PooledObject
        return new DefaultPooledObject<>(sftp);
    }

    /**
     * Validate the ChannelSftp
     * @param p
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<ChannelSftp> p) {
        ChannelSftp sftp = p.getObject();
        if( null == sftp ) {
            return false;
        }
        try {
            return sftp.isConnected() && !sftp.isClosed() && sftp.getSession().isConnected();
        } catch (JSchException e) {
            return false;
        }
    }

    /**
     * Destroy the ChannelSftp, if the Session is broken, destroy the session as well.
     * @param p
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<ChannelSftp> p) throws Exception {
        ChannelSftp sftp = p.getObject();
        if( null == sftp ) {
            return;
        }

        try { sftp.disconnect(); } catch (Throwable th) { }

        if( !sftp.getSession().isConnected() ){
            resetSession(sftp.getSession());
        }
    }

    /**
     * Set the ChannelSftp to home directory for next use
     * @param p
     * @throws Exception
     */
    @Override
    public void activateObject(PooledObject<ChannelSftp> p) throws Exception {
        ChannelSftp sftp =  p.getObject();
        sftp.cd(sftp.getHome());
    }
}
