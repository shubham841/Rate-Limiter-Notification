package com.example.demo.service;

import com.example.demo.model.UserLimit;
import com.example.demo.repository.UserLimitRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserLimitService {
    private final UserLimitRepo userLimitRepo;

    public UserLimitService(UserLimitRepo userLimitRepo) {
        this.userLimitRepo = userLimitRepo;
    }

    public ResponseEntity<String> save(UserLimit req) {
        if (req.getId() != null && userLimitRepo.existsById(req.getId())) {
            UserLimit existingConfig = userLimitRepo.findById(req.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Config not found with ID: " + req.getId()));
            existingConfig.setLimit(req.getLimit());
            userLimitRepo.save(existingConfig);
            return ResponseEntity.ok("Config updated successfully");
        } else {
            UserLimit config = new UserLimit();
            config.setLimit(req.getLimit());
            userLimitRepo.save(config);
            return ResponseEntity.ok("Config created successfully");
        }
    }

    public String getLimit(Long userId) {
        UserLimit config = userLimitRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Config not found for user ID: " + userId));
        return String.valueOf(config.getLimit());
    }
}
