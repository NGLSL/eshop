package xin.codedream.eshop.cache.listener;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

/**
 * Desc...
 *
 * @author LeiXinXin
 * @date 2020/3/27$
 */
@Slf4j
public class ProductCacheEventListener implements CacheEventListener<Object, Object> {
    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        log.info("person caching event {} {} {} {}",
                event.getType(),
                event.getKey(),
                event.getOldValue(),
                event.getNewValue());


    }
}
