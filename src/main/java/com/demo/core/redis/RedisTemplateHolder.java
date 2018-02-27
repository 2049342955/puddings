package com.demo.core.redis;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisTemplateHolder {
    private static RedisTemplate<String, String> redisTemplate;

    public RedisTemplateHolder() {
    }

    public static void holdRedisTemplate(RedisTemplate redisTemplate) {
        redisTemplate = redisTemplate;
    }

    public static RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }
}
