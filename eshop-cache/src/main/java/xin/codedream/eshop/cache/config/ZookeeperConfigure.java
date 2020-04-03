package xin.codedream.eshop.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Desc...
 *
 * @author LeiXinXin
 * @date 2020/4/3$
 */
@Configuration
@ConfigurationProperties("zookeeper")
public class ZookeeperConfigure {

    @Getter
    @Setter
    private String connectString;

    @Bean
    public CuratorFramework curatorFramework() {
        return CuratorFrameworkFactory.newClient(connectString, new RetryNTimes(5, 5000));
    }

}
