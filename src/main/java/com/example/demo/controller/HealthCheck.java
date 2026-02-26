package com.example.demo.controller;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    private final StringRedisTemplate redisTemplate;

    public HealthCheck(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/redis-test")
    public String redisTest() {
        redisTemplate.opsForValue().set("check", "connected");
        return redisTemplate.opsForValue().get("check");
    }
}
