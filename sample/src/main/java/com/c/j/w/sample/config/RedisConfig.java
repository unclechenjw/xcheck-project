package com.c.j.w.sample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenjw
 * @Date: 2017/9/5 下午5:43
 * @Description: Redis配置
 */
@Configuration
public class RedisConfig {


    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;

    JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMinIdle(2);
        return jedisPoolConfig;
    }

    JedisShardInfo jedisShardInfo() {
        JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port);
        jedisShardInfo.setPassword(password);
        return jedisShardInfo;
    }

    @Bean
    ShardedJedisPool shardedJedisPool() {
        List<JedisShardInfo> list = new ArrayList<>();
        list.add(jedisShardInfo());
        return new ShardedJedisPool(jedisPoolConfig(), list);
    }

}
