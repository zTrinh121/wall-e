package com.example.SWP391_Project.service;

import com.example.SWP391_Project.model.SystemNotification;

import java.util.List;

public interface WebhookService {
    void sendNotificationToAllUsers(String message);
}

