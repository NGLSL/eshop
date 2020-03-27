package xin.codedream.eshop.inventory.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xin.codedream.eshop.inventory.request.Request;
import xin.codedream.eshop.inventory.request.RequestQueue;
import xin.codedream.eshop.inventory.service.RequestAsyncProcessService;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 请求异步执行的Service Impl
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
@Slf4j
@Service
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {
    @Override
    public void process(Request request) throws InterruptedException {
        // 做请求路由，根据每个请求的商品ID，路由到对应的队列中去
        ArrayBlockingQueue<Request> queue = getRoutingQueue(request.getProductId());
        queue.put(request);
    }

    /**
     * 获取路由到的内存队列
     *
     * @param productId 商品ID
     * @return 内存队列
     */
    private ArrayBlockingQueue<Request> getRoutingQueue(Integer productId) {
        RequestQueue queue = RequestQueue.getInstance();
        int h;
        int hash = (productId == null) ? 0 : (h = productId.toString().hashCode()) ^ (h >>> 16);
        // 对Hash取模，将Hash值路由到指定的内存队列中
        // 用内存队列的数量对Hash值取模后，结果一定是在0-7之间，所以任何一个商品ID都会被固定路由到同样的一个内存队列中去
        int index = (queue.getQueueSize() - 1) & hash;
        log.info("路由内存队列，商品ID:{} 队列索引：{}", productId, index);
        return queue.getQueue(index);
    }

}
