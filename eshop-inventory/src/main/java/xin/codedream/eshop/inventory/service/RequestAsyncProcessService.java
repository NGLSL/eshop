package xin.codedream.eshop.inventory.service;

import xin.codedream.eshop.inventory.request.Request;

/**
 * 请求异步执行的Service
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
public interface RequestAsyncProcessService {
    /**
     * process
     *
     * @param request 请求
     * @throws InterruptedException 阻塞异常
     */
    void process(Request request) throws InterruptedException;
}
