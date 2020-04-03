package xin.codedream.eshop.cache.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xin.codedream.eshop.cache.model.ProductInfo;
import xin.codedream.eshop.cache.model.ShopInfo;
import xin.codedream.eshop.cache.service.CacheService;

/**
 * 缓存Controller
 *
 * @author LeiXinXin
 * @date 2020/3/26$
 */
@RestController
@AllArgsConstructor
@Slf4j
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
        cacheService.saveProductInfoToLocalCache(productInfo);
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
        return cacheService.getProductInfo(id);
    }

    /**
     * 获取店铺信息
     *
     * @param id 店铺Id
     * @return
     */
    @RequestMapping("/getShopInfo/{id}")
    public ShopInfo getShopInfo(@PathVariable Long id) {
        ShopInfo shopInfo = cacheService.getShopInfoFromLocalCache(id);
        log.info("从本地缓存获取店铺信息缓存，店铺ID:{}", id);
        if (shopInfo == null) {
            log.info("本地没有缓存，从Redis缓存获取店铺信息缓存，店铺ID:{}", id);
            return cacheService.getShopInfoFromRedisCache(id);
        }
        return shopInfo;
    }
}
