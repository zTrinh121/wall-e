package com.example.SWP391_Project.config;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/notification")
    @SendTo("/topic/notifications")
    public String sendNotification(String message) {
        return message;
    }
}
