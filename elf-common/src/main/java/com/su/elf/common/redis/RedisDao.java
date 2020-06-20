package com.su.elf.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author surongyao
 * @date 2018-05-29 08:36
 * @desc
 */
@Slf4j
@Component
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
        ops.set(key, value);
    }

    /**
     *
     * @Param:
     * @return:
     */
    public void set(String key, String value, long time, TimeUnit timeUnit){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, time, timeUnit);
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
    public void del(String key){
        redisTemplate.delete(key);
    }

    /**
     *
     * @Param:
     * @return:
     */
    public void expire(String key, long seconds, TimeUnit timeUnit){
        redisTemplate.expire(key, seconds, timeUnit);
    }

    /**
     * 根据 key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<String> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 查找匹配key
     * @param pattern key
     * @return /
     */
    public List<String> scan(String pattern) {
        ScanOptions options = ScanOptions.scanOptions().match(pattern).build();
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection rc = Objects.requireNonNull(factory).getConnection();
        Cursor<byte[]> cursor = rc.scan(options);
        List<String> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(new String(cursor.next()));
        }
        try {
            RedisConnectionUtils.releaseConnection(rc, factory, false);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

}
