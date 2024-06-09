package com.example.SWP391_Project.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CenterPostDto {

    String title;

    String content;

    Date createdAt;

    String file_url;

    Date updatedAt;

    String centerSendTo;
}
