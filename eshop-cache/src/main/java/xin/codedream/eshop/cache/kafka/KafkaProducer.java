package xin.codedream.eshop.cache.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Kafka生产者
 *
 * @author LeiXinXin
 * @date 2020/3/30$
 */
@Component
@Slf4j
public class KafkaProducer<T> {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<Object, Object> objectKafkaTemplate) {
        this.kafkaTemplate = objectKafkaTemplate;
    }

    /**
     * 发送消息
     *
     * @param topic    主题
     * @param obj      消息
     * @param callback 回调
     */
    public void send(String topic, T obj, ListenableFutureCallback<? super SendResult<Object, Object>> callback) {
        ListenableFuture<SendResult<Object, Object>> future = kafkaTemplate.send(topic, obj);
        if (callback != null) {
            future.addCallback(callback);
        }
    }
}
