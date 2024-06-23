package com.example.SWP391_Project.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@Getter
@Setter
public class CourseDetailResponse {

    // course's attribute
    private String name;
    private String code;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amountOfStudents;
    private String subjectName;
    private float courseFee;


    // teacher's attribute
    private String teacherName;
    private String teacherPhone;
    private String teacherAddress;
    private Date teacherDob;
    private boolean teacherGender;
    private String teacherEmail;

}
