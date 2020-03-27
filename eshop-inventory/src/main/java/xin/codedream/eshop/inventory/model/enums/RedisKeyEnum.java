package xin.codedream.eshop.inventory.model.enums;

import lombok.Getter;

/**
 * Redis Key 枚举类
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
@Getter
public enum RedisKeyEnum {
    /**
     * 产品库存 KEY 前缀
     */
    PRODUCT_INVENTORY("product:inventory:");

    private String keyPrefix;

    RedisKeyEnum(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
