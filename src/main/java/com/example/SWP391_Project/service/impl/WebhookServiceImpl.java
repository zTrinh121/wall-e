package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.model.SystemNotification;
import com.example.SWP391_Project.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebhookServiceImpl implements WebhookService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNotificationToAllUsers(String message) {
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}

