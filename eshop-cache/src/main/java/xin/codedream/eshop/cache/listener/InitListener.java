package xin.codedream.eshop.cache.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;
import xin.codedream.eshop.cache.business.CacheBusinessService;
import xin.codedream.eshop.cache.rebuild.RebuildCacheThread;
import xin.codedream.eshop.cache.thread.UserThreadFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 初始化监听器
 *
 * @author LeiXinXin
 * @date 2020/4/4$
 */
@Component
@Slf4j
public class InitListener implements ServletContextInitializer {
    private CuratorFramework curatorFramework;
    private CacheBusinessService cacheBusinessService;

    public InitListener(CuratorFramework curatorFramework, CacheBusinessService cacheBusinessService) {
        this.curatorFramework = curatorFramework;
        this.cacheBusinessService = cacheBusinessService;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.info("初始化需要的资源...");
        ExecutorService executors = new ThreadPoolExecutor(0, 1, 5,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
                new UserThreadFactory("init"));
        executors.execute(() -> curatorFramework.start());
        executors.execute(new RebuildCacheThread(cacheBusinessService));
    }
}
