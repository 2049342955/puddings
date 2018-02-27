package com.demo.core.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisCacheUtil<T> {
    public RedisTemplate redisTemplate;

    public RedisCacheUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operation = this.redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = this.redisTemplate.opsForValue();
        return operation.get(key);
    }

    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations listOperation = this.redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();

            for(int i = 0; i < size; ++i) {
                listOperation.rightPush(key, dataList.get(i));
            }
        }

        return listOperation;
    }

    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList();
        ListOperations<String, T> listOperation = this.redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for(int i = 0; (long)i < size; ++i) {
            dataList.add(listOperation.leftPop(key));
        }

        return dataList;
    }

    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = this.redisTemplate.boundSetOps(key);
        Iterator it = dataSet.iterator();

        while(it.hasNext()) {
            setOperation.add((T) new Object[]{it.next()});
        }

        return setOperation;
    }

    public Set<T> getCacheSet(String key) {
        Set<T> dataSet = new HashSet();
        BoundSetOperations<String, T> operation = this.redisTemplate.boundSetOps(key);
        Long size = operation.size();

        for(int i = 0; (long)i < size; ++i) {
            dataSet.add(operation.pop());
        }

        return dataSet;
    }

    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {
        HashOperations hashOperations = this.redisTemplate.opsForHash();
        if (null != dataMap) {
            Iterator var4 = dataMap.entrySet().iterator();

            while(var4.hasNext()) {
                Entry<String, T> entry = (Entry)var4.next();
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }

        return hashOperations;
    }

    public <T> Map<String, T> getCacheMap(String key) {
        Map<String, T> map = this.redisTemplate.opsForHash().entries(key);
        return map;
    }

    public <T> HashOperations<String, Integer, T> setCacheIntegerMap(String key, Map<Integer, T> dataMap) {
        HashOperations hashOperations = this.redisTemplate.opsForHash();
        if (null != dataMap) {
            Iterator var4 = dataMap.entrySet().iterator();

            while(var4.hasNext()) {
                Entry<Integer, T> entry = (Entry)var4.next();
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }

        return hashOperations;
    }

    public <T> Map<Integer, T> getCacheIntegerMap(String key) {
        Map<Integer, T> map = this.redisTemplate.opsForHash().entries(key);
        return map;
    }
}
