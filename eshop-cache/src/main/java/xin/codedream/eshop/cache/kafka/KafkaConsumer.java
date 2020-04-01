package xin.codedream.eshop.cache.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import xin.codedream.eshop.cache.handler.KafkaMessageHandlerChain;

import java.util.Optional;

/**
 * Kafka消费者
 *
 * @author LeiXinXin
 * @date 2020/3/30$
 */
@Component
@Slf4j
public class KafkaConsumer {
    private final KafkaMessageHandlerChain kafkaMessageHandlerChain;

    public KafkaConsumer(KafkaMessageHandlerChain kafkaMessageHandlerChain) {
        this.kafkaMessageHandlerChain = kafkaMessageHandlerChain;
    }

    @KafkaListener(topics = {"test", "cache-msg"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            // 看看怎么优化成适配器模式
            Object message = kafkaMessage.get();
            kafkaMessageHandlerChain.doProcess(message);
        }
    }
}
