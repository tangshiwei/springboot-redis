package com.redis.service;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Redis数据库默认16个
 */
@Component
public class JedisService {

    public Jedis getJedis(String hostname, int port) {
        Jedis jedis = new Jedis(hostname, port);
        return jedis;
    }

    public Jedis getJedis(String hostname, int port, String password) {
        Jedis jedis = new Jedis(hostname, port);
        jedis.auth(password);
        return jedis;
    }

    public void close(Jedis jedis) {
        if (jedis != null && jedis.isConnected()) {
            jedis.close();
        }
    }

    /**
     * 切换数据库
     */
    public String selectIndex(Jedis jedis, int dbIndex) {
        return jedis.select(dbIndex);
    }

    /**
     * 判断Key是否存在
     */
    public boolean isExists(Jedis jedis, String key) {
        return jedis.exists(key);
    }

    /**
     * 删除Key
     */
    public Long deleteKeys(Jedis jedis, String... keys) {
        return jedis.del(keys);
    }

    /**
     * 查看某个key的剩余生存时间,单位【秒】.永久生存或者不存在的都返回-1
     */
    public Long getTtl(Jedis jedis, String key) {
        return jedis.ttl(key);
    }

    /**
     * 移除某个key的生存时间
     */
    public Long persist(Jedis jedis, String key) {
        return jedis.persist(key);
    }

    /**
     * 查看key所储存的值的类型
     */
    public String getType(Jedis jedis, String key) {
        return jedis.type(key);
    }

    /**
     * 修改key名称
     */
    public String renameKey(Jedis jedis, String key, String newKey) {
        return jedis.rename(key, newKey);
    }

    /**
     * 将当前db的key移动到给定的db当中
     */
    public Long renameKey(Jedis jedis, String key, int dbIndex) {
        return jedis.move(key, dbIndex);
    }

    /**
     * 获取所有的key
     */
    public List<String> getKeyAll(Jedis jedis) {
        try {
            Set<String> keys = jedis.keys("*");
            return new ArrayList<>(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void set(Jedis jedis, String key, String value) {
        jedis.set(key, value);
    }

    // // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
    public void set(Jedis jedis, String key, String value, long second) {
        jedis.set(key, value, "NX", "EX", second);
    }

    public String get(Jedis jedis, String key) {
        return jedis.get(key);
    }

}
