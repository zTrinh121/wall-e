package com.example.SWP391_Project.service;

import com.example.SWP391_Project.dto.IndividualNotificationDto;
import com.example.SWP391_Project.dto.SystemNotificationDto;
import com.example.SWP391_Project.model.IndividualNotification;
import com.example.SWP391_Project.model.SystemNotification;
import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.model.ViewSystemNotification;
import com.example.SWP391_Project.response.CenterDetailResponse;
import com.example.SWP391_Project.response.CenterPostResponse;
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

    SystemNotification createSystemNotification(SystemNotificationDto systemNotificationDto);

    SystemNotification updateSystemNotification(int id, SystemNotificationDto systemNotificationDto);

    boolean deleteSystemNotification(int id);

//    List<ViewSystemNotification> getAllViewSystemNotifications();

    List<ViewSystemNotification> getListViewersSystemNotification(int notificationId);
    // --------------------------------------------------

    void approveCenterPost(int id);

    void rejectCenterPost(int id);

    void approveCenterApply(int id);

    void rejectCenterApply(int id);

    // ------------- Individual Notifications --------------
    List<IndividualNotification> getAllIndividualNotifications(int adminId);

    IndividualNotification createIndividualNotification(IndividualNotificationDto invididualNotificationDto, User admin);

    IndividualNotification updateIndividualNotification(int id, IndividualNotificationDto invididualNotificationDto);

    boolean deleteIndividualNotification(int id);

    List<IndividualNotification> getViewersIndividualNotification(int adminId);

    // --------------------------------------------------
}