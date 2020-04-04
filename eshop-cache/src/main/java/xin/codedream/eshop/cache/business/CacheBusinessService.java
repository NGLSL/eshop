package xin.codedream.eshop.cache.business;

import xin.codedream.eshop.cache.model.ProductInfo;

/**
 * 缓存业务接口
 *
 * @author LeiXinXin
 * @date 2020/4/4$
 */
public interface CacheBusinessService {
    /**
     * 将商品信息保存到Redis中和本地缓存中
     *
     * @param productInfo 商品信息
     * @throws Exception 异常
     */
    void saveProductCache(ProductInfo productInfo) throws Exception;

    /**
     * 从Redis缓存或者本地缓存中获取商品信息
     *
     * @param productId 商品id
     * @return 商品信息
     * @throws InterruptedException 异常
     */
    ProductInfo getProductInfo(Long productId) throws InterruptedException;

}
