package xin.codedream.cache.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xin.codedream.eshop.cache.CacheApplication;
import xin.codedream.eshop.cache.model.enums.ZookeeperLockEnum;

import java.util.concurrent.TimeUnit;

/**
 * ZK 分布式锁测试
 *
 * @author LeiXinXin
 * @date 2020/4/3$
 */
@SpringBootTest(classes = CacheApplication.class)
@Slf4j
public class ZookeeperLockTests {
    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void lockTest() throws Exception {
        new Thread(() -> {
            try {
                String productId = "1";
                String path = ZookeeperLockEnum.PRODUCT_LOCK.getPath() + productId;
                InterProcessMutex lock = new InterProcessMutex(curatorFramework, path);
                try {
                    if (lock.acquire(1, TimeUnit.SECONDS)) {
                        log.info("================lock1 获取到锁================");
                        Thread.sleep(4000);
                    }
                } finally {
                    boolean result = lock.isAcquiredInThisProcess();
                    if (result) {
                        lock.release();
                    }
                    Assert.assertTrue("lock1 没有获取到锁", result);
                }
            } catch (Exception e) {
                log.error("获取锁失败：", e);
            }
        }).start();

        Thread.sleep(500);

        String productId = "1";
        String path = ZookeeperLockEnum.PRODUCT_LOCK.getPath() + productId;
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, path);
        try {
            if (lock.acquire(1, TimeUnit.SECONDS)) {
                log.info("================lock2 获取到锁====================");
            }
        } finally {
            boolean result = lock.isAcquiredInThisProcess();
            if (lock.isAcquiredInThisProcess()) {
                lock.release();
            }
            Assert.assertFalse("lock2 获取到锁，lock1还在sleep中，这里不应该获取到锁", result);
        }
    }
}
