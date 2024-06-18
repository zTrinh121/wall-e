package com.example.SWP391_Project.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCoursesResponse {

    private String teacherCode;

    private String teacherName;

    private String teacherPhone;

    private String teacherAddress;

    private Date teacherDob;

    private boolean teacherGender;

    private String teacherEmail;

    private String courseNames;
}
