package xin.codedream.eshop.cache.service;

import xin.codedream.eshop.cache.model.ProductInfo;
import xin.codedream.eshop.cache.model.ShopInfo;

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
    ProductInfo saveProductInfoToLocalCache(ProductInfo productInfo);

    /**
     * 将店铺信息保存到缓存中
     *
     * @param shopInfo 店铺信息
     * @return 店铺信息
     */
    ShopInfo saveShopInfoToLocalCache(ShopInfo shopInfo);

    /**
     * 将商品信息保存到Redis中
     *
     * @param productInfo 商品信息
     */
    void saveProductInfoToRedisCache(ProductInfo productInfo);

    /**
     * 将商品信息保存到Redis中和本地缓存中
     *
     * @param productInfo       商品信息
     * @throws Exception 异常
     */
    void saveProductCache(ProductInfo productInfo) throws Exception;

    /**
     * 从Redis缓存或者本地缓存中获取商品信息
     *
     * @param productId 商品id
     * @return 商品信息
     */
    ProductInfo getProductInfo(Long productId);

    /**
     * 从本地缓存中获取商品信息
     *
     * @param productId 商品id
     * @return 商品信息
     */
    ProductInfo getProductInfoFromLocalCache(Long productId);

    /**
     * 从Redis缓存中获取商品信息
     *
     * @param productId 商品id
     * @return 商品信息
     */
    ProductInfo getProductInfoFromRedisCache(Long productId);

    /**
     * 将店铺信息保存到Redis中
     *
     * @param shopInfo 店铺信息
     */
    void saveShopInfoToRedisCache(ShopInfo shopInfo);

    /**
     * 从本地缓存中获取店铺信息
     *
     * @param shopId 店铺id
     * @return 店铺信息
     */
    ShopInfo getShopInfoFromLocalCache(Long shopId);

    /**
     * 从Redis缓存中获取店铺信息
     *
     * @param shopId 店铺id
     * @return 店铺信息
     */
    ShopInfo getShopInfoFromRedisCache(Long shopId);

}
