package xin.codedream.cache.kafka;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xin.codedream.eshop.cache.CacheApplication;
import xin.codedream.eshop.cache.model.ProductInfo;
import xin.codedream.eshop.cache.rebuild.RebuildCacheQueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 单例测试
 *
 * @author LeiXinXin
 * @date 2020/4/4$
 */
@SpringBootTest(classes = CacheApplication.class)
public class SingletonTests {

    @Test
    public void test() {
        ArrayBlockingQueue<ProductInfo> queue = RebuildCacheQueue.INSTANCE.getQueue();
        ArrayBlockingQueue<ProductInfo> queue2 = RebuildCacheQueue.INSTANCE.getQueue();

        Assert.assertEquals(queue, queue2);
    }
}
