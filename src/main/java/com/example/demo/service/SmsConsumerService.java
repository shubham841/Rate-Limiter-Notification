package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SmsConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(SmsConsumerService.class);

    @KafkaListener(topics = "sms-outbound", groupId = "sms-consumer-group")
    public void consumeMessage(String message) {
        logger.info("SMS Sent: {}", message);
    }
}
