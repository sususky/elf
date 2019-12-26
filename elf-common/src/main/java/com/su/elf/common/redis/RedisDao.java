package com.su.elf.common.redis;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * @author surongyao
 * @date 2018-05-29 08:36
 * @desc
 */
public class RedisDao {

    private StringRedisTemplate redisTemplate;

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *
     * @Param:
     * @return:
     */
    public void set(String key, String value){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key,value);
    }

    /**
     *
     * @Param:
     * @return:
     */
    public String get(String key){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    /**
     *
     * @Param:
     * @return:
     */
    public void hset(String table, String key, String value){
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(table);
        ops.put(key, value);
    }

    /**
     *
     * @Param:
     * @return:
     */
    public String hget(String table, String key){
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(table);
        return ops.get(key);
    }

    /**
     *
     * @Param:
     * @return:
     */
    public long increase(String key, Integer value){
        return redisTemplate.opsForValue().increment(key, value);
    }

    /**
     *
     * @Param:
     * @return:
     */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     *
     * @Param:
     * @return:
     */
    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    /**
     *
     * @Param:
     * @return:
     */
    public void expire(String key, int seconds, TimeUnit timeUnit){
        redisTemplate.expire(key, seconds, timeUnit);
    }

}
