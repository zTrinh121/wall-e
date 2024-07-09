package com.example.SWP391_Project.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSalaryResponse {
    private Long teacherId;
    private String teacherName;
    private Double totalSalary;
}
