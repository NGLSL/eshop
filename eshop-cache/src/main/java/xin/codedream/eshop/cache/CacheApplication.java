package xin.codedream.eshop.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * InventoryApplication
 *
 * @author LeiXinXin
 * @date 2020/3/25
 */
@SpringBootApplication(scanBasePackages = "xin.codedream.eshop.cache")
@MapperScan("xin.codedream.eshop.cache.mapper")
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

}
