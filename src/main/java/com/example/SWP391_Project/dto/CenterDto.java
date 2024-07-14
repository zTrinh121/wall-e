package com.example.SWP391_Project.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CenterDto {

    // course's detail
    String code;

    String name;
    String description;
    String address;
    String phone;
    String email;
    String regulation;
    String imagePath;

    // manager's detail
    int managerId;
}
