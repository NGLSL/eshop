package xin.codedream.eshop.cache.handler;

/**
 * kafka消息处理器
 *
 * @author LeiXinXin
 * @date 2020/3/30$
 */
public interface KafkaMessageHandler {
    /**
     * 消息处理
     *
     * @param message 消息
     * @throws Exception 异常
     */
    void process(Object message) throws Exception;
}
