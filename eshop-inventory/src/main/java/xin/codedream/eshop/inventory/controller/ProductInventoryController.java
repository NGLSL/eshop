package xin.codedream.eshop.inventory.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xin.codedream.eshop.inventory.model.ProductInventory;
import xin.codedream.eshop.inventory.model.vo.Response;
import xin.codedream.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import xin.codedream.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import xin.codedream.eshop.inventory.request.Request;
import xin.codedream.eshop.inventory.service.ProductInventoryService;
import xin.codedream.eshop.inventory.service.RequestAsyncProcessService;

/**
 * 商品库存 Controller
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
@RestController
@AllArgsConstructor
@Slf4j
public class ProductInventoryController {

    private ProductInventoryService productInventoryService;
    private RequestAsyncProcessService asyncProcessService;


    /**
     * 更新商品库存
     *
     * @param productInventory 商品库存
     * @return Response
     */
    @PostMapping("/updateProductInventory")
    public Response updateProductInventory(@RequestBody ProductInventory productInventory) {
        try {
            log.info("接收到更新商品库存的请求，商品ID:{} 商品库存数量：{}", productInventory.getProductId(), productInventory.getInventoryCnt());
            Request request = new ProductInventoryDBUpdateRequest(productInventory, productInventoryService);
            asyncProcessService.process(request);
            return new Response(Response.SUCCESS);
        } catch (Exception e) {
            log.error("更新失败：", e);
            return new Response(Response.FAILURE);
        }
    }

    /**
     * 获取商品库存
     *
     * @param productId 商品ID
     * @return Response
     */
    @GetMapping("/getProductInventory/{productId}")
    public ProductInventory getProductInventory(@PathVariable Integer productId) {
        log.info("接收到一个商品库存读请求，商品ID:{}", productId);
        ProductInventory productInventory;
        try {
            Request request = new ProductInventoryCacheRefreshRequest(productInventoryService, productId, false);
            asyncProcessService.process(request);
            long startTime = System.currentTimeMillis();
            long waitTime = 0L;
            // 等待超过200毫秒，没有从缓存中读取到
            // 测试改为了25000
            int maxWaitTime = 25000;
            while (waitTime <= maxWaitTime) {
                // 尝试去Redis读取缓存
                productInventory = productInventoryService.getProductInventoryCache(productId);
                if (productInventory != null) {
                    log.info("在200ms内读取到了，缓存数据，商品Id:{} 商品库存数量：{}", productId, productInventory.getInventoryCnt());
                    return productInventory;
                } else {
                    Thread.sleep(20);
                    long endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;
                }
            }
            // 尝试从数据库读取数据
            productInventory = productInventoryService.findProductInventory(productId);
            if (productInventory != null) {
                // 将缓存刷新
                // 这个过程实际上也是一个读请求，但是没有放到队列中串行去处理，还是有数据不一致的问题
                request = new ProductInventoryCacheRefreshRequest(productInventoryService, productId, true);
                asyncProcessService.process(request);
                // 代码运行到这里只有三种情况：
                // 1. 上次也是读请求，数据刷入了Redis，但是Redis LRU 算法清理掉了，标志位还是false
                // 所以，此时下一个读请求是从缓存中拿不到数据的，再放一个Request进队列，让数据去刷新一下
                // 2. 可能在200ms内，队列一直积压着，没有等待到它执行，所以就直接查询一直数据库，然后给队列塞进去一个刷新的请求
                // 3.数据库本来就没有，出现缓存穿透，每次都请求MySQL
                return productInventory;
            }
        } catch (Exception e) {
            log.error("更新失败：", e);
        }
        return new ProductInventory(productId, -1L);
    }
}
