package com.redis.controller;

import com.redis.bean.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class UserController {

    @RequestMapping("/getUser")
    @Cacheable(value="user")
    public User getUser(@RequestParam(name="username") String username) {
        User user=new User();
        user.setUsername(username);
        user.setPassword("aa123456");
        user.setEmail("aa@126.com");
        user.setAddress("aaa");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;

    }

    //Session共享
    @RequestMapping("/uid")
    @Cacheable(value="session")
    public String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
