package xin.codedream.eshop.inventory.mapper;

import org.springframework.stereotype.Repository;
import xin.codedream.eshop.inventory.model.ProductInventory;

/**
 * 库存数量Mapper
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
@Repository
public interface ProductInventoryMapper {
    /**
     * 更新库存数量
     *
     * @param productInventory 商品库存
     * @return
     */
    int updateProductInventory(ProductInventory productInventory);

    /**
     * 根据商品Id查询商品库存信息
     *
     * @param productId
     * @return
     */
    ProductInventory getByProductId(Integer productId);
}
