package xin.codedream.eshop.cache.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 请求响应
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    private String status;
    private String msg;

    public Response(String msg) {
        this.msg = msg;
    }

}
