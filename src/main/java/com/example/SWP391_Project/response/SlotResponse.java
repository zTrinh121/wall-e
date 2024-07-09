package com.example.SWP391_Project.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotResponse {

    private int slotId;
    private Date slotDate;
    private Date slotStartTime;
    private Date slotEndTime;

    private int courseId;
    private String courseName;

    private Boolean attendanceStatus;
}
