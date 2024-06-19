package com.example.SWP391_Project.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCoursesResponse {
    private String studentCode;

    private String studentName;

    private String studentPhone;

    private String studentAddress;

    private Date studentDob;

    private boolean studentGender;

    private String studentEmail;

    private String courseNames;
}
