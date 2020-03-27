package xin.codedream.eshop.inventory.request;

/**
 * 请求接口
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
public interface Request {
    /**
     * process
     */
    void process();

    /**
     * 获取商品Id
     *
     * @return
     */
    Integer getProductId();

    /**
     * 获取强制刷新标志
     *
     * @return 强制刷新标志
     */
    default boolean isForceRefresh() {
        return false;
    }
}
