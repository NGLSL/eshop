package xin.codedream.eshop.cache.service;

import xin.codedream.eshop.cache.model.ProductInfo;

/**
 * 缓存接口
 *
 * @author LeiXinXin
 * @date 2020/3/26$
 */
public interface CacheService {
    /**
     * 将商品信息保存到缓存中
     *
     * @param productInfo 商品信息
     * @return 商品信息
     */
    ProductInfo saveLocalCache(ProductInfo productInfo);

    /**
     * 从本地缓存中获取商品信息
     *
     * @param id 商品ID
     * @return 商品信息
     */
    ProductInfo getLocalCache(Long id);
}
