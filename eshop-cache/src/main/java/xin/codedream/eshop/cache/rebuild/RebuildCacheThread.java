package xin.codedream.eshop.cache.rebuild;

import lombok.extern.slf4j.Slf4j;
import xin.codedream.eshop.cache.model.ProductInfo;
import xin.codedream.eshop.cache.service.CacheService;

/**
 * 缓存重建线程
 *
 * @author LeiXinXin
 * @date 2020/4/4$
 */
@Slf4j
public class RebuildCacheThread implements Runnable {
    private CacheService cacheService;

    public RebuildCacheThread(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ProductInfo productInfo = RebuildCacheQueue.INSTANCE.takeProductInfo();
                // 被动刷入缓存
                cacheService.saveProductCache(productInfo);
            } catch (Exception e) {
                log.error("刷入缓存失败:", e);
            }
        }
    }
}
