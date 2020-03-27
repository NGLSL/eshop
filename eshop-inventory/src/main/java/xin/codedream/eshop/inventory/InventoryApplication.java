package xin.codedream.eshop.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * InventoryApplication
 *
 * @author LeiXinXin
 * @date 2020/3/25
 */
@SpringBootApplication(scanBasePackages = "xin.codedream.eshop.inventory")
@MapperScan("xin.codedream.eshop.inventory.mapper")
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

}
