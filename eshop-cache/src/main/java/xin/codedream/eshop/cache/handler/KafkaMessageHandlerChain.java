package xin.codedream.eshop.cache.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * kafka消息处理链
 *
 * @author LeiXinXin
 * @date 2020/3/30$
 */
@Slf4j
public class KafkaMessageHandlerChain {
    private Map<String, KafkaMessageHandler> map;
    private ObjectMapper objectMapper;

    public KafkaMessageHandlerChain() {
        map = new HashMap<>();
        objectMapper = new ObjectMapper();
    }

    public KafkaMessageHandlerChain addLast(KafkaMessageHandler handler) {
        map.put(handler.getClass().getSimpleName(), handler);
        return this;
    }

    public void doProcess(Object msg) throws Exception {
        HashMap<String, Object> hashMap = objectMapper.convertValue(msg, HashMap.class);
        String serviceId = hashMap.get("serviceId").toString();
        if (serviceId != null && !"".equals(serviceId)) {
            KafkaMessageHandler handler = map.get(serviceId);
            handler.process(hashMap);
        }
    }
}
