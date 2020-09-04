package com.redis;

import com.redis.service.JedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClient {
    @Autowired
    private JedisService jedisService;

    @Test
    public void testObj() throws Exception {
        try (Jedis jedis = jedisService.getJedis("localhost", 6379, "123456")) {

            Long aLong = jedis.dbSize();
            System.out.println(aLong);

            Long db = jedis.getDB();
            System.out.println("db="+aLong);


            String dbname = jedis.select(5);
            System.out.println(dbname);
            jedis.set("ccc", "aaa");
        }

    }

}
