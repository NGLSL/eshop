package xin.codedream.cache.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.SpringBootTest;
import xin.codedream.eshop.cache.CacheApplication;
import xin.codedream.eshop.cache.handler.KafkaMessageHandlerChain;
import xin.codedream.eshop.cache.kafka.KafkaProducer;
import xin.codedream.eshop.cache.callback.ProductCallback;
import xin.codedream.eshop.cache.model.ProductInfo;
import xin.codedream.eshop.cache.model.ShopInfo;

/**
 * Kafka测试
 *
 * @author LeiXinXin
 * @date 2020/3/30$
 */
@SpringBootTest(classes = CacheApplication.class)
public class KafkaTests {
    @Autowired
    private KafkaProducer<Object> producer;
    @Autowired
    private ProductCallback productCallback;

    @Test
    public void producer() throws JsonProcessingException {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(1L);
        productInfo.setName("Mac");
        productInfo.setPrice(1000.00);
        productInfo.setServiceId("ProductInfoMessageHandler");
        productInfo.setService("暂无售后");
        productInfo.setColor("white");
        productInfo.setPictureList("1.jpg");
        productInfo.setSize("15.5");
        productInfo.setSpecification("16G/512G/I7");
        productInfo.setShopId(1L);
        producer.send("test", productInfo, productCallback);
    }

    @Test
    public void shop() throws InterruptedException {
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(1L);
        shopInfo.setServiceId("ShopInfoMessageHandler");
        shopInfo.setLevel("1");
        shopInfo.setName("百年老店");
        shopInfo.setGoodCommentRate("98%");
        producer.send("test", shopInfo, null);
        Thread.sleep(2000L);
    }
}
