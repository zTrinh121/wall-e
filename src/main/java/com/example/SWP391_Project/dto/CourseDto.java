package com.example.SWP391_Project.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDto {
    String name;
    String code;
    String description;
    Date startDate;
    Date endDate;
    int amountOfStudents;
    float courseFee;
    float totalCourseFee;
    String centerCode;
    String subjectName;
    String teacherCode;
}
