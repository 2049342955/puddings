package com.demo.core.cache;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

public class HashRedisCacheManager extends RedisCacheManager {
    private RedisOperations redisOperations;
    private Map<String, Long> expires;
    private long defaultExpiration = 0L;

    public HashRedisCacheManager(RedisOperations redisOperations) {
        super(redisOperations);
        this.redisOperations = redisOperations;
    }

    public HashRedisCacheManager(RedisOperations redisOperations, Collection<String> cacheNames) {
        super(redisOperations, cacheNames);
        this.redisOperations = redisOperations;
    }

    protected Cache getMissingCache(String name) {
        return new HashRedisCache(name, this.redisOperations, this.computeExpiration(name));
    }

    protected long computeExpiration(String name) {
        Long[] expiration = new Long[]{null};
        if (this.expires != null) {
            if (this.expires.get(name) != null) {
                expiration[0] = (Long)this.expires.get(name);
            } else {
                this.expires.forEach((key, value) -> {
                    if (Pattern.compile(key).matcher(name).find()) {
                        expiration[0] = value;
                    }
                });
            }
        }

        return expiration[0] != null ? expiration[0] : this.defaultExpiration;
    }

    public void setDefaultExpiration(long defaultExpireTime) {
        super.setDefaultExpiration(defaultExpireTime);
        this.defaultExpiration = defaultExpireTime;
    }

    public void setExpires(Map<String, Long> expires) {
        super.setExpires(expires);
        this.expires = expires;
    }
}
