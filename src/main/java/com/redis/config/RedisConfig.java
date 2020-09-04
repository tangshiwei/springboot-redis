package com.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DigestUtils;

import java.lang.reflect.Method;

/**
 * redis的key生成策略配置
 */
@Slf4j
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName()).append(".");
                sb.append(method.getName());
                sb.append("&");
                try {
                    if(params!=null&&params.length>0) {
                        JSONArray jsonArray = new JSONArray(params);
                        sb.append(jsonArray.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String key = DigestUtils.sha1DigestAsHex(sb.toString());
                log.info("\n redis cache key str: " + sb.toString());

                log.info("\n redis cache key sha256Hex: " + key);

                return key;
            }
        };
    }
}
