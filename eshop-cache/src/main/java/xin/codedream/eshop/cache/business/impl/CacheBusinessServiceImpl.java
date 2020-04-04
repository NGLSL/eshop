package xin.codedream.eshop.cache.business.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.stereotype.Service;
import xin.codedream.eshop.cache.business.CacheBusinessService;
import xin.codedream.eshop.cache.model.ProductInfo;
import xin.codedream.eshop.cache.model.enums.ZookeeperLockEnum;
import xin.codedream.eshop.cache.rebuild.RebuildCacheQueue;
import xin.codedream.eshop.cache.service.CacheService;

import java.util.concurrent.TimeUnit;

/**
 * 缓存业务接口实现
 *
 * @author LeiXinXin
 * @date 2020/4/4$
 */
@Slf4j
@Service
public class CacheBusinessServiceImpl implements CacheBusinessService {
    private final CacheService cacheService;
    private final CuratorFramework zkClient;

    public CacheBusinessServiceImpl(CacheService cacheService, CuratorFramework curatorFramework) {
        this.cacheService = cacheService;
        this.zkClient = curatorFramework;
    }

    @Override
    public void saveProductCache(ProductInfo productInfo) throws Exception {
        // 将数据写入Redis之前，需要先获取一个分布式锁
        String path = ZookeeperLockEnum.PRODUCT_LOCK.getPath() + productInfo.getId();
        InterProcessMutex lock = new InterProcessMutex(zkClient, path);
        boolean acquire = lock.acquire(5, TimeUnit.SECONDS);
        if (acquire) {
            try {
                ProductInfo localProductInfo = cacheService.getProductInfoFromLocalCache(productInfo.getId());
                if (localProductInfo == null) {
                    cacheService.saveProductInfoToLocalCache(productInfo);
                } else if (productInfo.getModifyTime().after(localProductInfo.getModifyTime())) {
                    cacheService.saveProductInfoToLocalCache(productInfo);
                }
                ProductInfo existsProductInfo = cacheService.getProductInfoFromRedisCache(productInfo.getId());
                if (existsProductInfo == null) {
                    cacheService.saveProductInfoToRedisCache(productInfo);
                } else if (productInfo.getModifyTime().after(existsProductInfo.getModifyTime())) {
                    // redis中存在的数据，它的修改时间要小于当前这个数据的时间，才能写入到redis
                    cacheService.saveProductInfoToRedisCache(productInfo);
                }
            } finally {
                lock.release();
            }
        }
    }

    @Override
    public ProductInfo getProductInfo(Long productId) throws InterruptedException {
        ProductInfo productInfo = cacheService.getProductInfoFromLocalCache(productId);
        log.info("从本地缓存获取商品信息缓存，商品ID:{}", productId);
        if (productInfo == null) {
            log.info("本地没有缓存，从Redis缓存获取商品信息缓存，商品ID:{}", productId);
            productInfo = cacheService.getProductInfoFromRedisCache(productId);
            RebuildCacheQueue.INSTANCE.putProductInfo(productInfo);
        }
        return productInfo;
    }
}
