package xin.codedream.eshop.cache.rebuild;

import lombok.extern.slf4j.Slf4j;
import xin.codedream.eshop.cache.business.CacheBusinessService;
import xin.codedream.eshop.cache.model.ProductInfo;

/**
 * 缓存重建线程
 *
 * @author LeiXinXin
 * @date 2020/4/4$
 */
@Slf4j
public class RebuildCacheThread implements Runnable {
    private CacheBusinessService cacheBusinessService;

    public RebuildCacheThread(CacheBusinessService cacheBusinessService) {
        this.cacheBusinessService = cacheBusinessService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ProductInfo productInfo = RebuildCacheQueue.INSTANCE.takeProductInfo();
                log.info("接收到队列的数据，即将进行处理，商品ID:{}", productInfo.getId());
                // 被动刷入缓存
                cacheBusinessService.saveProductCache(productInfo);
            } catch (Exception e) {
                log.error("刷入缓存失败:", e);
            }
        }
    }
}
