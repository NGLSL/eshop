package xin.codedream.eshop.cache.model;

import lombok.*;

import java.io.Serializable;

/**
 * 店铺信息
 *
 * @author LeiXinXin
 * @date 2020/3/30$
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShopInfo implements Serializable {
    private Long id;
    private String serviceId;
    private String name;
    private String level;
    private String goodCommentRate;
}
