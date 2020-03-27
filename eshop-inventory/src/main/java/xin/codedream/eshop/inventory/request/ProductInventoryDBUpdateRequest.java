package xin.codedream.eshop.inventory.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xin.codedream.eshop.inventory.model.ProductInventory;
import xin.codedream.eshop.inventory.service.ProductInventoryService;

/**
 * 数据更新请求
 * <p>
 * cache aside patten
 * <p>
 * （1） 删除请求
 * （2） 更新数据库
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
@Slf4j
@AllArgsConstructor
public class ProductInventoryDBUpdateRequest implements Request {
    /**
     * 商品库存
     */
    private ProductInventory productInventory;
    /**
     * 商品库存Service
     */
    private ProductInventoryService productInventoryService;

    @Override
    public void process() {
        // 删除Redis中的缓存
        log.info("数据库更新请求开始执行，商品ID:{} 商品库存数量：{}", productInventory.getProductId(), productInventory.getInventoryCnt());
        productInventoryService.removeProductInventory(productInventory);
        try {
            // 这里是为了测试
            Thread.sleep(20000);
        } catch (Exception e) {

        }
        // 修改数据库中的库存
        productInventoryService.updateProductInventory(productInventory);

    }

    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }
}
