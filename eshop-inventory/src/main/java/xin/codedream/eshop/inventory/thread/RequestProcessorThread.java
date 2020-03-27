package xin.codedream.eshop.inventory.thread;

import lombok.extern.slf4j.Slf4j;
import xin.codedream.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import xin.codedream.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import xin.codedream.eshop.inventory.request.Request;
import xin.codedream.eshop.inventory.request.RequestQueue;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 执行请求的工作线程
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
@Slf4j
public class RequestProcessorThread implements Runnable {
    /**
     * 内存队列
     */
    private ArrayBlockingQueue<Request> queues;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queues) {
        this.queues = queues;
    }

    @Override
    public void run() {
        try {
            RequestQueue requestQueue = RequestQueue.getInstance();
            Request request;
            Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();
            while (true) {
                request = queues.take();
                boolean forceRefresh = request.isForceRefresh();
                // 先做去重
                if (!forceRefresh) {
                    if (request instanceof ProductInventoryDBUpdateRequest) {
                        // 如果是一个更新数据库的请求，那么就将对应的标志设置为True
                        flagMap.put(request.getProductId(), true);
                    } else if (request instanceof ProductInventoryCacheRefreshRequest) {
                        //  如果是缓存刷新的请求，那么就判断，如果标识不为空而且是true，就说明之前有一个更新数据库的请求
                        Boolean flag = flagMap.get(request.getProductId());
                        if (flag != null && flag.equals(true)) {
                            flagMap.put(request.getProductId(), false);
                        }
                        if (flag == null) {
                            flagMap.put(request.getProductId(), false);
                        }
                        // 如果是刷新缓存的请求，而且表示不为空，但是标识是false，说明前面有一个数据库更新请求和一个缓存刷新请求了
                        if (flag != null && flag.equals(false)) {
                            // 把刷新缓存请求过滤掉
                            log.info("请求被过滤了，无需刷新缓存，商品ID:{}", request.getProductId());
                            continue;
                        }
                    }
                }
                request.process();
                /*
                 * 假如说，执行完了一个读请求后，假设数据已经刷新到redis中了，但是后面Redis可能会因为内存不足，自动清理掉了
                 * 如果数据被Redis清理掉了以后，后面又来一个请求，此时发现标志位是false，就不会去执行这个操作了
                 * 所以，在执行完读请求后，实际上这个标志位停留在false
                 */
            }
        } catch (Exception e) {
            log.error("执行请求失败：", e);
        }
    }
}
