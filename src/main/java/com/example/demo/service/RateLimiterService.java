package com.example.demo.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;
    private final UserLimitService userLimitService;

    public RateLimiterService(StringRedisTemplate redisTemplate,
                              UserLimitService userLimitService) {
        this.redisTemplate = redisTemplate;
        this.userLimitService = userLimitService;
    }

    public boolean isAllowed(Long userId) {

        Integer limit = getUserLimit(userId);

        String rateKey = "rate:" + userId + ":" + getCurrentHourKey();

        Long currentCount = redisTemplate.opsForValue().increment(rateKey);

        if (currentCount == null) {
            return false;
        }

        if (currentCount == 1) {
            redisTemplate.expire(rateKey, Duration.ofSeconds(secondsUntilEndOfHour()));
        }

        return currentCount <= limit;
    }

    private Integer getUserLimit(Long userId) {

        String configKey = "config:" + userId;

        String cachedLimit = redisTemplate.opsForValue().get(configKey);

        if (cachedLimit != null) {
            return Integer.parseInt(cachedLimit);
        }

        // fetching value from database and caching it for 10 minutes
        Integer dbLimit = Integer.parseInt(userLimitService.getLimit(userId));

        redisTemplate.opsForValue().set(
                configKey,
                dbLimit.toString(),
                Duration.ofMinutes(10)
        );

        return dbLimit;
    }

    private String getCurrentHourKey() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH"));
    }

    private long secondsUntilEndOfHour() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfHour = now.truncatedTo(ChronoUnit.HOURS).plusHours(1);
        return Duration.between(now, endOfHour).getSeconds();
    }
}