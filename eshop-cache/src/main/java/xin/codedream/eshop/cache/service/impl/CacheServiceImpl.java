package xin.codedream.eshop.cache.service.impl;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xin.codedream.eshop.cache.model.ProductInfo;
import xin.codedream.eshop.cache.service.CacheService;

/**
 * 缓存Service 实现类
 *
 * @author LeiXinXin
 * @date 2020/3/26$
 */
@Service
public class CacheServiceImpl implements CacheService {
    private static final String CACHE_NAME = "local";

    @CachePut(cacheNames = CACHE_NAME, key = "'key_'+#productInfo.getId()")
    @Override
    public ProductInfo saveLocalCache(ProductInfo productInfo) {
        return productInfo;
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "'key_'+#id")
    public ProductInfo getLocalCache(Long id) {
        return null;
    }
}
