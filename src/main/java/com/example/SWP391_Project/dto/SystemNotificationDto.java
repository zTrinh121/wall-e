package com.example.SWP391_Project.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SystemNotificationDto {

    String title;

    String content;

    Date createdAt;

    Date updatedAt;
}
