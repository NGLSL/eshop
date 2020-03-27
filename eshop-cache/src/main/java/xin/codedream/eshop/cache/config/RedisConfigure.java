package xin.codedream.eshop.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * Redis配置
 *
 * @author LeiXinXin
 * @date 2019/7/29
 */
@Configuration
public class RedisConfigure {

    @Bean("redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory rcf) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(rcf);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setDefaultSerializer(stringRedisSerializer);
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }
}
