package xin.codedream.eshop.inventory.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xin.codedream.eshop.inventory.mapper.ProductInventoryMapper;
import xin.codedream.eshop.inventory.model.ProductInventory;
import xin.codedream.eshop.inventory.model.enums.RedisKeyEnum;
import xin.codedream.eshop.inventory.service.ProductInventoryService;

import java.util.Optional;

/**
 * 商品库存Service Impl
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
@Slf4j
@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {
    private ProductInventoryMapper productInventoryMapper;
    private RedisTemplate<Object, Object> redisTemplate;

    public ProductInventoryServiceImpl(ProductInventoryMapper productInventoryMapper, RedisTemplate<Object, Object> redisTemplate) {
        this.productInventoryMapper = productInventoryMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateProductInventory(productInventory);
        log.info("已修改数据库中的库存，商品Id:{} 商品数量:{}", productInventory.getProductId(), productInventory.getInventoryCnt());
    }

    @Override
    public void removeProductInventory(ProductInventory productInventory) {
        String key = RedisKeyEnum.PRODUCT_INVENTORY.getKeyPrefix() + productInventory.getProductId();
        redisTemplate.delete(key);
        log.info("删除Redis中的缓存，key:{}", key);
    }

    @Override
    public ProductInventory findProductInventory(Integer productId) {
        return productInventoryMapper.getByProductId(productId);
    }

    @Override
    public void setProductInventoryCache(ProductInventory productInventory) {
        String key = RedisKeyEnum.PRODUCT_INVENTORY.getKeyPrefix() + productInventory.getProductId();
        redisTemplate.opsForValue().set(key, productInventory.getInventoryCnt().toString());
        log.info("已更新商品库存的缓存，商品ID：{} 商品库存数量：{} Key:{}", productInventory.getProductId(), productInventory.getInventoryCnt(), key);
    }

    @Override
    public ProductInventory getProductInventoryCache(Integer productId) {
        String key = RedisKeyEnum.PRODUCT_INVENTORY.getKeyPrefix() + productId;
        Object o = redisTemplate.opsForValue().get(key);
        long inventoryCnt;
        Optional<Object> optional = Optional.ofNullable(o);
        boolean isExists = optional.isPresent();
        if (isExists && !o.toString().isEmpty()) {
            inventoryCnt = Long.parseLong(o.toString());
            return new ProductInventory(productId, inventoryCnt);
        }
        return null;
    }
}
