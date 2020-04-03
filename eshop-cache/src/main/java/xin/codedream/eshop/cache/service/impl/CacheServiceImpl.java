package xin.codedream.eshop.cache.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xin.codedream.eshop.cache.model.ProductInfo;
import xin.codedream.eshop.cache.model.ShopInfo;
import xin.codedream.eshop.cache.model.enums.RedisKeyEnum;
import xin.codedream.eshop.cache.model.enums.ZookeeperLockEnum;
import xin.codedream.eshop.cache.rebuild.RebuildCacheQueue;
import xin.codedream.eshop.cache.service.CacheService;

import java.util.concurrent.TimeUnit;

/**
 * 缓存Service 实现类
 *
 * @author LeiXinXin
 * @date 2020/3/26$
 */
@Service
@Slf4j
public class CacheServiceImpl implements CacheService {
    private static final String CACHE_NAME = "local";
    private final RedisTemplate<String, Object> redisTemplate;
    private final CuratorFramework zkClient;

    public CacheServiceImpl(RedisTemplate<String, Object> redisTemplate, CuratorFramework curatorFramework) {
        this.redisTemplate = redisTemplate;
        this.zkClient = curatorFramework;
    }

    @CachePut(cacheNames = CACHE_NAME, key = "'product:info:' + #productInfo.getId()")
    @Override
    public ProductInfo saveProductInfoToLocalCache(ProductInfo productInfo) {
        return productInfo;
    }

    @CachePut(cacheNames = CACHE_NAME, key = "'shop:info:'+#shopInfo.getId()")
    @Override
    public ShopInfo saveShopInfoToLocalCache(ShopInfo shopInfo) {
        return shopInfo;
    }

    @Override
    public void saveProductInfoToRedisCache(ProductInfo productInfo) {
        redisTemplate.opsForValue().set(RedisKeyEnum.PRODUCT_INFO.getKeyPrefix() + productInfo.getId(), productInfo);
    }

    @Override
    public void saveProductCache(ProductInfo productInfo, boolean isWriteLocalCache) throws Exception {
        if (isWriteLocalCache) {
            // 保存到本地缓存
            this.saveProductInfoToLocalCache(productInfo);
        }
        // 将数据写入Redis之前，需要先获取一个分布式锁
        String path = ZookeeperLockEnum.PRODUCT_LOCK.getPath() + productInfo.getId();
        InterProcessMutex lock = new InterProcessMutex(zkClient, path);
        boolean acquire = lock.acquire(5, TimeUnit.SECONDS);
        if (acquire) {
            try {
                ProductInfo existsProductInfo = this.getProductInfoFromRedisCache(productInfo.getId());
                // redis中存在的数据，它的修改时间要小于当前这个数据的时间，才能写入到redis
                boolean isBefore = existsProductInfo != null && existsProductInfo.getModifyTime() != null
                        && existsProductInfo.getModifyTime().before(productInfo.getModifyTime());
                if (isBefore) {
                    this.saveProductInfoToRedisCache(productInfo);
                }
            } finally {
                lock.release();
            }
        }
    }

    @Override
    public ProductInfo getProductInfo(Long productId) {
        ProductInfo productInfo = this.getProductInfoFromLocalCache(productId);
        log.info("从本地缓存获取商品信息缓存，商品ID:{}", productId);
        if (productInfo == null) {
            log.info("本地没有缓存，从Redis缓存获取商品信息缓存，商品ID:{}", productId);
            productInfo = this.getProductInfoFromRedisCache(productId);
            try {
                // 将数据推送到一个队列中，重新刷入缓存中
                RebuildCacheQueue.INSTANCE.putProductInfo(productInfo);
            } catch (InterruptedException e) {
                log.error("将商品信息，推送到内存队列失败：", e);
            }
        }
        return productInfo;
    }

    @Cacheable(cacheNames = CACHE_NAME, key = "'product:info:'+#productId")
    @Override
    public ProductInfo getProductInfoFromLocalCache(Long productId) {
        return null;
    }

    @Override
    public ProductInfo getProductInfoFromRedisCache(Long productId) {
        return (ProductInfo) redisTemplate.opsForValue().get(RedisKeyEnum.PRODUCT_INFO.getKeyPrefix() + productId);
    }

    @Override
    public void saveShopInfoToRedisCache(ShopInfo shopInfo) {
        redisTemplate.opsForValue().set(RedisKeyEnum.SHOP_INFO.getKeyPrefix() + shopInfo.getId(), shopInfo);
    }

    @Cacheable(cacheNames = CACHE_NAME, key = "'shop:info:'+#shopId")
    @Override
    public ShopInfo getShopInfoFromLocalCache(Long shopId) {
        return null;
    }

    @Override
    public ShopInfo getShopInfoFromRedisCache(Long shopId) {
        return (ShopInfo) redisTemplate.opsForValue().get(RedisKeyEnum.SHOP_INFO.getKeyPrefix() + shopId);
    }
}
