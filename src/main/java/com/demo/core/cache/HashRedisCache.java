package com.demo.core.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;

public class HashRedisCache<T> implements Cache {
    private String name;
    private long expiration;
    private RedisOperations redisOperations;

    public HashRedisCache(String name, RedisOperations redisOperations) {
        this.name = name;
        this.redisOperations = redisOperations;
    }

    public HashRedisCache(String name, RedisOperations redisOperations, long expiration) {
        this.name = name;
        this.redisOperations = redisOperations;
        this.expiration = expiration;
    }

    public String getName() {
        return this.name;
    }

    public Object getNativeCache() {
        return this.redisOperations;
    }

    public ValueWrapper get(Object key) {
        HashOperations hashOperations = this.redisOperations.opsForHash();
        Object t = hashOperations.get(this.getName(), key);
        this.redisOperations.expire(this.name, this.expiration, TimeUnit.SECONDS);
        return new SimpleValueWrapper(t);
    }

    public <T> T get(Object key, Class<T> aClass) {
        return (T) this.get(key).get();
    }

    public List<T> getAll() {
        HashOperations hashOperations = this.redisOperations.opsForHash();
        Map map = hashOperations.entries(this.getName());
        List<T> result = new ArrayList();
        if (map.size() > 0) {
            result.addAll(map.values());
        }

        this.redisOperations.expire(this.name, this.expiration, TimeUnit.SECONDS);
        return result;
    }

    public <T> T get(Object key, Callable<T> callable) {
        return (T) this.get(key).get();
    }

    public void put(Object key, Object value) {
        HashOperations hashOperations = this.redisOperations.opsForHash();
        hashOperations.put(this.getName(), key, value);
        this.redisOperations.expire(this.name, this.expiration, TimeUnit.SECONDS);
    }

    public void putAll(final Map<Object, Object> values) {
        final byte[] rawKey = this.redisOperations.getKeySerializer().serialize(this.getName());
        final long expiration = this.expiration;
        this.redisOperations.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Set<Object> keySet = values.keySet();
                Iterator it = keySet.iterator();

                while(it.hasNext()) {
                    Object key = it.next();
                    connection.hSet(rawKey, HashRedisCache.this.redisOperations.getKeySerializer().serialize(key), HashRedisCache.this.redisOperations.getValueSerializer().serialize(values.get(key)));
                }

                connection.expire(rawKey, expiration);
                return null;
            }
        });
    }

    public ValueWrapper putIfAbsent(Object key, Object value) {
        this.put(key, value);
        return this.get(key);
    }

    public void evict(Object key) {
        HashOperations hashOperations = this.redisOperations.opsForHash();
        hashOperations.delete(this.getName(), new Object[]{key});
    }

    public void clear() {
        HashOperations hashOperations = this.redisOperations.opsForHash();
        Set keys = hashOperations.keys(this.getName());
        Iterator var3 = keys.iterator();

        while(var3.hasNext()) {
            Object key = var3.next();
            hashOperations.delete(this.getName(), new Object[]{key});
        }

    }
}
