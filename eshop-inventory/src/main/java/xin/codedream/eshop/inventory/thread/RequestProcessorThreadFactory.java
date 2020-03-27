package xin.codedream.eshop.inventory.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 请求线程工厂
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
public class RequestProcessorThreadFactory implements ThreadFactory {
    private String namePrefix;
    private AtomicInteger atomicInteger = new AtomicInteger(1);

    public RequestProcessorThreadFactory(String workerOrGroupName) {
        this.namePrefix = workerOrGroupName + "-worker-";
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = namePrefix + atomicInteger.incrementAndGet();
        return new Thread(r, name);
    }
}
