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

    @Configuration
    @ConfigurationProperties("motan")
    public static class MotanProperties {
        private String zookeeper;

        public String getZookeeper() {
            return zookeeper;
        }

        public void setZookeeper(String zookeeper) {
            this.zookeeper = zookeeper;
        }
    }@Bean
    public AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage("ft.biz.motan");
        return motanAnnotationBean;
    }

    @Bean(name = "motan-protocol")
    public ProtocolConfigBean protocolConfig() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setName("motan");
        config.setDefault(true);
        config.setMaxContentLength(1048576);
        return config;
    }

    @Bean(name = "motan-registry")
    public RegistryConfigBean registryConfig(MotanProperties properties) {
        RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol("zookeeper");
        config.setAddress(properties.getZookeeper());
        return config;
    }

    @Bean(name = "motan-service")
    public BasicServiceConfigBean basicServiceConfig() {
        BasicServiceConfigBean config = new BasicServiceConfigBean();
        config.setExport("motan-protocol:16001");
        config.setGroup("fund-transfer");
        config.setAccessLog(false);
        config.setShareChannel(true);
        config.setModule("ft-rpc");
        config.setApplication("ft");
        config.setRegistry("motan-registry");
        return config;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void turnOnMotanHeartbeat() {
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    }

}
