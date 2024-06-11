package com.example.SWP391_Project.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SlotDto {

    Date slotDate;
    Date slotStartTime;
    Date slotEndTime;

    String courseCode;
    String roomName;
}
