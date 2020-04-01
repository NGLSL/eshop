package xin.codedream.eshop.cache.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * 商品回调
 *
 * @author LeiXinXin
 * @date 2020/3/30$
 */
@Slf4j
@Component
public class ProductCallback implements ListenableFutureCallback<SendResult<Object, Object>> {

    @Override
    public void onFailure(Throwable ex) {
        log.error("失败回调：", ex);
    }

    @Override
    public void onSuccess(SendResult<Object, Object> result) {
        ProducerRecord<Object, Object> producerRecord = result.getProducerRecord();
        log.info("成功回调，Topic:{} Value:{}", producerRecord.topic(), producerRecord.value());
    }
}
