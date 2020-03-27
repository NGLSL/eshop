package xin.codedream.eshop.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 库存数量Model
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventory {
    /**
     * 产品Id
     */
    private Integer productId;
    /**
     * 库存数量
     */
    private Long inventoryCnt;
}
