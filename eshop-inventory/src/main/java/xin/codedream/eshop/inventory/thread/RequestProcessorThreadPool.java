package xin.codedream.eshop.inventory.thread;

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
public class RequestProcessorThreadPool {
    private static RequestProcessorThreadPool instance;

    private RequestProcessorThreadPool() {
        init();
    }

    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getInstance();
    }
    private void init() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100), new RequestProcessorThreadFactory("Request"),
                new ThreadPoolExecutor.AbortPolicy());
        RequestQueue requestQueue = RequestQueue.getInstance();
        for (int i = 0; i < threadPoolExecutor.getCorePoolSize(); i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<>(100);
            requestQueue.addQueue(queue);
            threadPoolExecutor.execute(new RequestProcessorThread(queue));
        }
    }

    private static class Singleton {
        static {
            instance = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance() {
            return instance;
        }
    }
}
