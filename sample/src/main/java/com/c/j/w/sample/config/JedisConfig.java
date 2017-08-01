package com.c.j.w.sample.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 16/10/3.
 */
@Configuration
public class JedisConfig implements EnvironmentAware {

    private Environment environment;

    JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMinIdle(2);
        return jedisPoolConfig;
    }

    JedisShardInfo jedisShardInfo() {
        JedisShardInfo jedisShardInfo = new JedisShardInfo(
                environment.getProperty("redis.host"),
                Integer.parseInt(environment.getProperty("redis.port")));
        jedisShardInfo.setPassword(environment.getProperty("redis.password"));
        return jedisShardInfo;
    }

    @Bean
    ShardedJedisPool shardedJedisPool() {
        List<JedisShardInfo> list = new ArrayList<>();
        list.add(jedisShardInfo());
        return new ShardedJedisPool(jedisPoolConfig(), list);
    }

//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName(environment.getProperty("redis.host"));
//        jedisConnectionFactory.setDatabase(0);
//        jedisConnectionFactory.setPort();
//        jedisConnectionFactory.setPassword(environment.getProperty("redis.password"));
//        jedisConnectionFactory.setUsePool(true);
//        return jedisConnectionFactory;
//    }
//
//    RedisTemplate redisTemplate() {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        return redisTemplate;
//    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
