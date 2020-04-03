package xin.codedream.eshop.cache.model.enums;

import lombok.Getter;

/**
 * ZK 分布式锁path枚举
 *
 * @author LeiXinXin
 * @date 2020/4/3$
 */
@Getter
public enum  ZookeeperLockEnum {
    /**
     * 商品Lock枚举
     */
    PRODUCT_LOCK("/product-lock-"),
    ;

    private String path;

    ZookeeperLockEnum(String path) {
        this.path = path;
    }
}
