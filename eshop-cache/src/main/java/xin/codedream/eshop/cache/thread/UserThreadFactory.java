package xin.codedream.eshop.cache.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
public class UserThreadFactory implements ThreadFactory {
    private String namePrefix;
    private AtomicInteger atomicInteger = new AtomicInteger(1);

    public UserThreadFactory(String workerOrGroupName) {
        this.namePrefix = workerOrGroupName + "-worker-";
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = namePrefix + atomicInteger.incrementAndGet();
        return new Thread(r, name);
    }
}
