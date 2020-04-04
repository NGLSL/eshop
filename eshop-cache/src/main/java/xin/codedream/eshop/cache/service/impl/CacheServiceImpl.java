package xin.codedream.eshop.cache.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xin.codedream.eshop.cache.model.ProductInfo;
import xin.codedream.eshop.cache.model.ShopInfo;
import xin.codedream.eshop.cache.model.enums.RedisKeyEnum;
import xin.codedream.eshop.cache.service.CacheService;

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

    public CacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
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
