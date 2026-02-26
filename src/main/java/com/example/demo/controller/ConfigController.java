package com.example.demo.controller;


import com.example.demo.model.UserLimit;
import com.example.demo.service.UserLimitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConfigController {

    private final UserLimitService userLimitService;

    public ConfigController(UserLimitService userLimitService) {
        this.userLimitService = userLimitService;
    }

    @PostMapping("/config")
    public ResponseEntity<?> updateConfig(@RequestBody UserLimit userLimit) {
        return userLimitService.save(userLimit);
    }


}
