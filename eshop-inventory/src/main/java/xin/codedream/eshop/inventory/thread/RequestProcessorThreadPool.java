package xin.codedream.eshop.inventory.thread;

import lombok.Getter;
import xin.codedream.eshop.inventory.request.Request;
import xin.codedream.eshop.inventory.request.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 请求线程池：单例
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
public enum RequestProcessorThreadPool {
    /**
     * 单例
     */
    INSTANCE;
    @Getter
    private ThreadPoolExecutor threadPoolExecutor;

    RequestProcessorThreadPool() {
        init();
    }

    private void init() {
        threadPoolExecutor = new ThreadPoolExecutor(10, 10, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100), new RequestProcessorThreadFactory("Request"),
                new ThreadPoolExecutor.AbortPolicy());
        RequestQueue requestQueue = RequestQueue.getInstance();
        for (int i = 0; i < threadPoolExecutor.getCorePoolSize(); i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<>(100);
            requestQueue.addQueue(queue);
            threadPoolExecutor.execute(new RequestProcessorThread(queue));
        }
    }

}
