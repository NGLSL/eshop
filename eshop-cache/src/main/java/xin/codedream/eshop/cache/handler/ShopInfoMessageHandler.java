package xin.codedream.eshop.cache.handler;

import lombok.extern.slf4j.Slf4j;
import xin.codedream.eshop.cache.model.ShopInfo;
import xin.codedream.eshop.cache.service.CacheService;

/**
 * 店铺消息处理器
 *
 * @author LeiXinXin
 * @date 2020/3/30$
 */
@Slf4j
public class ShopInfoMessageHandler implements KafkaMessageHandler {
    private final CacheService cacheService;

    public ShopInfoMessageHandler(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void process(Object message) {
        log.info("[ShopInfoMessageHandler]接收到数据");
        if (message instanceof ShopInfo) {
            ShopInfo shopInfo = (ShopInfo) message;
            cacheService.saveShopInfoToLocalCache(shopInfo);
            cacheService.saveShopInfoToRedisCache(shopInfo);
        }
    }
}
