package xin.codedream.eshop.inventory.request;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xin.codedream.eshop.inventory.model.ProductInventory;
import xin.codedream.eshop.inventory.service.ProductInventoryService;

/**
 * 重新加载商品库存的缓存
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
@Slf4j
@AllArgsConstructor
public class ProductInventoryCacheRefreshRequest implements Request {
    private ProductInventoryService productInventoryService;
    private Integer productId;
    /**
     * 是否强制刷新缓存
     */
    @Setter
    private boolean forceRefresh;

    @Override
    public void process() {
        // 根据商品Id获取库存信息
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        log.info("已查询到商品库存的数量，商品Id：{} 库存数量：{}", productId, productInventory.getInventoryCnt());
        // 刷新缓存
        productInventoryService.setProductInventoryCache(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productId;
    }

    @Override
    public boolean isForceRefresh() {
        return forceRefresh;
    }
}
