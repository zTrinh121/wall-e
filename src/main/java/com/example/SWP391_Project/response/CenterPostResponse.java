package com.example.SWP391_Project.response;

import com.example.SWP391_Project.enums.Status;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@Getter
@Setter
public class CenterPostResponse {

    // Center Post's attribute
    private int id;
    private String title;
    private String content;
    private Status status;
    private String fileUrl;
    private Date createdAt;
    private Date updatedAt;

    // Center's attribute
    private String centerName;
    private String centerAddress;
    private String centerEmail;

    // Manager's attribute
    private String managerName;
    private String managerPhone;
    private String managerAddress;
    private String managerEmail;

}
