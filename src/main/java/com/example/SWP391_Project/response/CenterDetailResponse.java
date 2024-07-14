package com.example.SWP391_Project.response;

import com.example.SWP391_Project.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@Getter
@Setter
public class CenterDetailResponse {

    // center's attribute
    private int id;
    private String code;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String email;
    private String regulation;
    private Date createdAt;
    private Status status;
    private String imagePath;

    // manager's attribute
    private String managerName;
    private String managerPhone;
    private String managerAddress;
    private String managerEmail;
}
