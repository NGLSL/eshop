package xin.codedream.eshop.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.curator.framework.CuratorFramework;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import xin.codedream.eshop.cache.handler.KafkaMessageHandlerChain;
import xin.codedream.eshop.cache.handler.ProductInfoMessageHandler;
import xin.codedream.eshop.cache.handler.ShopInfoMessageHandler;
import xin.codedream.eshop.cache.service.CacheService;

/**
 * InventoryApplication
 *
 * @author LeiXinXin
 * @date 2020/3/25
 */
@SpringBootApplication(scanBasePackages = "xin.codedream.eshop.cache")
@MapperScan("xin.codedream.eshop.cache.mapper")
public class CacheApplication implements CommandLineRunner {

    private final CuratorFramework curatorFramework;

    public CacheApplication(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public KafkaMessageHandlerChain kafkaMessageHandlerChain(CacheService cacheService) {
        KafkaMessageHandlerChain chain = new KafkaMessageHandlerChain();
        chain.addLast(new ProductInfoMessageHandler(cacheService))
                .addLast(new ShopInfoMessageHandler(cacheService));
        return chain;
    }

    @Override
    public void run(String... args) throws Exception {
        curatorFramework.start();
    }
}
