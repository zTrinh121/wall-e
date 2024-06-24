package com.example.SWP391_Project.response;

import com.example.SWP391_Project.model.CenterNotification;
import com.example.SWP391_Project.model.IndividualNotification;
import com.example.SWP391_Project.model.SystemNotification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
public class NotificationResponse {
    private List<IndividualNotification> individualNotifications;
    private List<CenterNotification> centerNotifications;
    private List<SystemNotification> systemNotifications;
}
