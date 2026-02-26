package com.example.demo.service;


import com.example.demo.producer.NotificationProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    private final RateLimiterService rateLimiterService;
    private final NotificationProducer notificationProducer;

    public NotificationService(RateLimiterService rateLimiterService, NotificationProducer notificationProducer) {
        this.rateLimiterService = rateLimiterService;
        this.notificationProducer = notificationProducer;
    }

    public ResponseEntity<String> sendNotification(String message, String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }
        Long userIdLong=Long.parseLong(userId);
        // also take id as character not only long, integer
        // example - shubham123, ravi142,ravi141 etc.
        
        boolean allowed = rateLimiterService.isAllowed(userIdLong);
        if (allowed) {
            notificationProducer.sendMessage(message);
            return ResponseEntity.ok("Notification sent successfully");

        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Rate limit exceeded");        }
    }
}
