package xin.codedream.eshop.cache.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.ProducerListener;

/**
 * Kafka生产者监听器
 *
 * @author LeiXinXin
 * @date 2020/3/30$
 */
@Slf4j
public class KafkaProducerListener<K, V> implements ProducerListener<K, V> {
    @Override
    public void onError(String topic, Integer partition, K key, V value, Exception exception) {
        String logOutput = "消息生产失败" +
                ", Topic:" + topic +
                ", Key:" + key +
                ", Value:" + value +
                ", Exception:" + exception;
        log.error(logOutput);
    }
}
