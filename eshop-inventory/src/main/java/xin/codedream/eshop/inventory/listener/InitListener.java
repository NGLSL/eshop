package xin.codedream.eshop.inventory.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xin.codedream.eshop.inventory.thread.RequestProcessorThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 系统初始化监听器
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
@Slf4j
@Component
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 初始化线程池和内存队列
        ThreadPoolExecutor threadPoolExecutor = RequestProcessorThreadPool.INSTANCE.getThreadPoolExecutor();
        log.info("请求线程池初始化，线程池核心数量：{}", threadPoolExecutor.getCorePoolSize());
    }
}
