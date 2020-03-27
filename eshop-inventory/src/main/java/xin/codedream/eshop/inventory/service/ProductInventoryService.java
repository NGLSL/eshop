package xin.codedream.eshop.inventory.service;

import xin.codedream.eshop.inventory.model.ProductInventory;

/**
 * 商品库存Service
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
public interface ProductInventoryService {
    /**
     * 更新商品库存
     *
     * @param productInventory 商品库存
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 删除Redis库存缓存
     *
     * @param productInventory 商品库存
     */
    void removeProductInventory(ProductInventory productInventory);

    /**
     * 根据商品Id获取商品信息
     *
     * @param productId 商品ID
     * @return
     */
    ProductInventory findProductInventory(Integer productId);

    /**
     * 设置商品库存缓存
     *
     * @param productInventory 商品库存
     */
    void setProductInventoryCache(ProductInventory productInventory);

    /**
     * 获取商品库存的缓存
     *
     * @param productId 商品Id
     * @return 商品库存
     */
    ProductInventory getProductInventoryCache(Integer productId);
}
