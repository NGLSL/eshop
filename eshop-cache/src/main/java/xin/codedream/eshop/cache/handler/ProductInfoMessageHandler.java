package xin.codedream.eshop.cache.handler;

import lombok.extern.slf4j.Slf4j;
import xin.codedream.eshop.cache.model.ProductInfo;
import xin.codedream.eshop.cache.service.CacheService;

/**
 * 商品消息处理器
 *
 * @author LeiXinXin
 * @date 2020/3/30$
 */
@Slf4j
public class ProductInfoMessageHandler implements KafkaMessageHandler {
    private final CacheService cacheService;

    public ProductInfoMessageHandler(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void process(Object message) throws Exception {
        log.info("[ProductInfoMessageHandler]接收到数据");
        if (message instanceof ProductInfo) {
            ProductInfo productInfo = (ProductInfo) message;
            cacheService.saveProductCache(productInfo, true);
        }
    }
}
