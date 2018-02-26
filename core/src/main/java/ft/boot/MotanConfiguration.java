package ft.boot;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class MotanConfiguration {

    @Configuration @ConfigurationProperties("motan")
    static class Properties {
        private String zookeeper;

        public String getZookeeper() {
            return zookeeper;
        }

        public void setZookeeper(String zookeeper) {
            this.zookeeper = zookeeper;
        }
    }

    @Bean
    AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage("ft.biz.motan");
        return motanAnnotationBean;
    }

    @Bean(name = "motan-protocol")
    ProtocolConfigBean protocolConfig() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setName("motan");
        config.setDefault(true);
        config.setMaxContentLength(1048576);
        config.setRequestTimeout(5 * 1000);
        return config;
    }

    @Bean(name = "motan-registry")
    RegistryConfigBean registryConfig(Properties properties) {
        RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol("zookeeper");
        config.setAddress(properties.getZookeeper());
        config.setRequestTimeout(5 * 1000);
        return config;
    }

    @Bean(name = "motan-service")
    BasicServiceConfigBean basicServiceConfig() {
        BasicServiceConfigBean config = new BasicServiceConfigBean();
        config.setExport("motan-protocol:16001");
        config.setApplication("ft");
        config.setModule("ft-rpc");
        config.setGroup("fund-transfer");
        config.setAccessLog(false);
        config.setShareChannel(true);
        config.setRegistry("motan-registry");
        config.setRequestTimeout(5 * 1000);
        return config;
    }

    @EventListener(ApplicationReadyEvent.class)
    void turnOnMotanHeartbeat() {
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    }

}
