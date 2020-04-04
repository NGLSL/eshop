package xin.codedream.eshop.cache.rebuild;

import lombok.Getter;
import xin.codedream.eshop.cache.model.ProductInfo;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 重建缓存队列
 *
 * @author LeiXinXin
 * @date 2020/4/4$
 */
public enum RebuildCacheQueue {
    /**
     * 单例
     */
    INSTANCE;

    @Getter
    private ArrayBlockingQueue<ProductInfo> queue;

    RebuildCacheQueue() {
        queue = new ArrayBlockingQueue<>(1000);
    }

    public void putProductInfo(ProductInfo productInfo) throws InterruptedException {
        if (productInfo == null) {
            return;
        }
        queue.put(productInfo);
    }

    public ProductInfo takeProductInfo() throws InterruptedException {
        return queue.take();
    }
}
