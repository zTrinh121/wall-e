package com.example.SWP391_Project.response;

import com.example.SWP391_Project.model.PrivateNotification;
import com.example.SWP391_Project.model.PublicNotification;
import com.example.SWP391_Project.model.SystemNotification;

import java.util.List;

// để làm chung 3 loại thông báo thành 1
public class AllNotificationResponse {
    private List<PrivateNotification> privateNotifications;
    private List<PublicNotification> publicNotifications;
    private List<SystemNotification> systemNotifications;

    public AllNotificationResponse(List<PrivateNotification> privateNotifications, List<PublicNotification> publicNotifications, List<SystemNotification> systemNotifications) {
        this.privateNotifications = privateNotifications;
        this.publicNotifications = publicNotifications;
        this.systemNotifications = systemNotifications;
    }

    // Getters and setters
    public List<PrivateNotification> getPrivateNotifications() {
        return privateNotifications;
    }

    public void setPrivateNotifications(List<PrivateNotification> privateNotifications) {
        this.privateNotifications = privateNotifications;
    }

    public List<PublicNotification> getPublicNotifications() {
        return publicNotifications;
    }

    public void setPublicNotifications(List<PublicNotification> publicNotifications) {
        this.publicNotifications = publicNotifications;
    }

    public List<SystemNotification> getSystemNotifications() {
        return systemNotifications;
    }

    public void setSystemNotifications(List<SystemNotification> systemNotifications) {
        this.systemNotifications = systemNotifications;
    }
}
