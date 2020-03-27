package xin.codedream.eshop.cache.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 商品信息
 *
 * @author LeiXinXin
 * @date 2020/3/26$
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo implements Serializable {
    private Long id;
    private String name;
    private Double price;
}
