package xin.codedream.eshop.cache.handler;

import lombok.extern.slf4j.Slf4j;
import xin.codedream.eshop.cache.business.CacheBusinessService;
import xin.codedream.eshop.cache.model.ProductInfo;

/**
 * 商品消息处理器
 *
 * @author LeiXinXin
 * @date 2020/3/30$
 */
@Slf4j
public class ProductInfoMessageHandler implements KafkaMessageHandler {
    private final CacheBusinessService cacheBusinessService;

    public ProductInfoMessageHandler(CacheBusinessService cacheBusinessService) {
        this.cacheBusinessService = cacheBusinessService;
    }

    @Override
    public void process(Object message) throws Exception {
        log.info("[ProductInfoMessageHandler]接收到数据");
        if (message instanceof ProductInfo) {
            ProductInfo productInfo = (ProductInfo) message;
            // 主动刷入缓存
            log.info("Kafka主动刷新缓存，商品Id:{}", productInfo.getId());
            cacheBusinessService.saveProductCache(productInfo);
        }
    }
}
