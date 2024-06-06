package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.SystemNotificationDto;
import com.example.SWP391_Project.model.PrivateNotification;
import com.example.SWP391_Project.model.PrivateNotificationDto;
import com.example.SWP391_Project.model.SystemNotification;
import com.example.SWP391_Project.response.CenterDetailResponse;
import com.example.SWP391_Project.response.CenterPostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    long getUserCount();

    long getManagement();

    List<CenterDetailResponse> getCenters();

    List<CenterPostResponse> getCenterPosts();


    // ------------- System Notifications --------------
    List<SystemNotification> getAllSystemNotifications();

    public Page<SystemNotification> getAllSystemNotifications(Pageable pageable);

    SystemNotification createSystemNotification(SystemNotificationDto systemNotificationDto);

    SystemNotification updateSystemNotification(int id, SystemNotificationDto systemNotificationDto);

    boolean deleteSystemNotification(int id);
    // --------------------------------------------------

    void approveCenterPost(int id);

    void rejectCenterPost(int id);

    void approveCenterApply(int id);

    void rejectCenterApply(int id);

    // ------------- Private Notifications --------------
    List<PrivateNotification> getAllPrivateNotifications();

    PrivateNotification createPrivateNotification(PrivateNotificationDto privateNotificationDtoDto);

    PrivateNotification updatePrivateNotification(int id, PrivateNotificationDto privateNotificationDto);

    boolean deletePrivateNotification(int id);
    // --------------------------------------------------
}
