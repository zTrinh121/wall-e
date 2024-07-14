package com.example.SWP391_Project.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date; // Thay vì import java.time.LocalDate và java.time.LocalTime

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SlotDto {

    Date slotDate; // Giữ nguyên kiểu dữ liệu là java.util.Date
    Date slotStartTime; // Giữ nguyên kiểu dữ liệu là java.util.Date
    Date slotEndTime; // Giữ nguyên kiểu dữ liệu là java.util.Date

    String courseCode;
    String roomName;
}
