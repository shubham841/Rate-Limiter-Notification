package com.example.demo.controller;


import com.example.demo.dto.NotificationRequest;
import com.example.demo.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SendController{

    private final NotificationService notificationService;

    public SendController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    //call notification service to send notification to user
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        return notificationService.sendNotification(request.getMessage(), request.getUserId());
    }





}
