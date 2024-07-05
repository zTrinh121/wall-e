package com.example.SWP391_Project.response;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DuplicateSlotInfo {
    private Date slotDate;
    private Date slotStartTime;
    private Date slotEndTime;
    private String courseName;
}
