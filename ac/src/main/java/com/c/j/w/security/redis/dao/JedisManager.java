package com.c.j.w.security.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.PostConstruct;

/**
 *
 * @Author chenjw
 * @Date 2016年12月06日
 */
@Component
public class JedisManager {

    @Autowired(required = false)
    private ShardedJedisPool jedisPool;

    public Long incr(String key) {
        ShardedJedis jedis = getJedis();
        try {
            return jedis.incr(key);
        } finally {
            jedis.close();
        }
    }

    public void expire(String key, int second) {
        ShardedJedis jedis = getJedis();
        try {
            jedis.expire(key, second);
        } finally {
            jedis.close();
        }
    }

    public long del(String key) {
        ShardedJedis jedis = getJedis();
        try {
            return jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    public void set(String key, int seconds, String value) {
        ShardedJedis jedis = getJedis();
        try {
            jedis.setex(key, seconds, value);
        } finally {
            jedis.close();
        }
    }

    private ShardedJedis getJedis() {
        return jedisPool.getResource();
    }

    @PostConstruct
    public void checkDataSource() {
        if (jedisPool == null) {
            throw new IllegalStateException("安全码校验工具依赖Jedis,请配置数据源后使用");
        }
    }
}
