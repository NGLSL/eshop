package xin.codedream.eshop.cache.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xin.codedream.eshop.cache.model.ProductInfo;
import xin.codedream.eshop.cache.service.CacheService;

/**
 * 缓存Controller
 *
 * @author LeiXinXin
 * @date 2020/3/26$
 */
@RestController
@AllArgsConstructor
public class CacheController {
    private CacheService cacheService;

    /**
     * 测试保存缓存
     *
     * @param productInfo
     * @return
     */
    @PostMapping("testPutCache")
    public String testPutCache(@RequestBody ProductInfo productInfo) {
        cacheService.saveLocalCache(productInfo);
        return "success";
    }

    /**
     * 获取商品信息
     *
     * @param id 商品Id
     * @return
     */
    @RequestMapping("/getProductInfo/{id}")
    public ProductInfo getProductInfo(@PathVariable Long id) {
        return cacheService.getLocalCache(id);
    }
}
