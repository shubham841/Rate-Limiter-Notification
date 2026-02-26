package com.example.demo.dto;

public class NotificationRequest {
    private String userId;
    private String message;

    public NotificationRequest() {
    }

    public NotificationRequest(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}


