package xin.codedream.eshop.cache.model;

import lombok.*;

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
@ToString
public class ProductInfo implements Serializable {
    private Long id;
    private String name;
    private Double price;
    private String pictureList;
    private String specification;
    private String serviceId;
    private String color;
    private String size;
    private Long shopId;
}
