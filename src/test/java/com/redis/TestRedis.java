package com.redis;

import com.redis.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObj() throws Exception {

        User user=new User();
        user.setUsername("aa");
        user.setPassword("aa123456");
        user.setEmail("aa@126.com");
        user.setAddress("aaa");
        ValueOperations<String, User> operations=redisTemplate.opsForValue();
        operations.set("user", user);
        operations.set("user2", user,1, TimeUnit.SECONDS);
        Thread.sleep(1000);

        boolean exists=redisTemplate.hasKey("user");
        if(exists){
            System.out.println("exists is true");
        }else{
            System.out.println("exists is false");
        }
        User userBean = operations.get("user");

        System.out.println(userBean);
        // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }

}
