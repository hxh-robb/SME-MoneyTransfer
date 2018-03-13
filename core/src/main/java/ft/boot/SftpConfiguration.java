package ft.boot;

import com.jcraft.jsch.ChannelSftp;
import ft.ex.pool.ChannelSftpFactory;
import ft.ex.pool.ThreadLocalObjectPoolDecorator;
import ft.files.FileManager;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * Configuration for SFTP connection
 */
@Configuration @ConfigurationProperties(prefix="sftp")
public class SftpConfiguration {
    private String host; // SFTP host
    private Integer port; // SFTP port
    private String username; // SFTP username
    private String password; // SFTP password
    private Integer connectTimeout; // SFTP connect timeout

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

//    private Documents docs; // Shared documents
//
//    public Documents getDocs() {
//        return docs;
//    }
//
//    public void setDocs(Documents docs) {
//        this.docs = docs;
//    }
//
//    /**
//     * Property holder class for "sftp.docs"
//     */
//    public static final class Documents {
//        /**
//         * raw - raw files path;
//         * img - image files path;
//         * cert - certification files path;
//         */
//        private String raw, img, cert;
//
//        public String getRaw() {
//            return raw;
//        }
//
//        public void setRaw(String raw) {
//            this.raw = raw;
//        }
//
//        public String getImg() {
//            return img;
//        }
//
//        public void setImg(String img) {
//            this.img = img;
//        }
//
//        public String getCert() {
//            return cert;
//        }
//
//        public void setCert(String cert) {
//            this.cert = cert;
//        }
//    }

    /**
     * Annotated Apache Commons Pool configuration
     */
    @Configuration @ConfigurationProperties(prefix = "sftp.pool")
    static class PoolConfig extends GenericObjectPoolConfig {
        public PoolConfig() {
            // Verify the SFTP connection as much as possible
            setTestOnBorrow(true);
            setTestOnCreate(true);
            setTestOnReturn(true);
            setTestWhileIdle(true);

            // Disable JMX register, because spring boot will register the JMX management bean for it.
            setJmxEnabled(false);
        }
    }

    @Bean
    ObjectPool<ChannelSftp> sftpConnectionPool(ChannelSftpFactory factory, PoolConfig config){
        return new GenericObjectPool<>(factory, config);
        // return new ThreadLocalObjectPoolDecorator(new GenericObjectPool<>(factory, config));
    }

    @Autowired
    private FileManager fm;

    @EventListener
    public void initSFTP(ApplicationReadyEvent event){
    }
}
