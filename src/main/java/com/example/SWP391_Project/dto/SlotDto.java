package com.example.SWP391_Project.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.util.Date; // Thay vì import java.time.LocalDate và java.time.LocalTime

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SlotDto {

    Date slotDate; // Giữ nguyên kiểu dữ liệu là java.util.Date
    Time slotStartTime; // Giữ nguyên kiểu dữ liệu là java.util.Date
    Time slotEndTime; // Giữ nguyên kiểu dữ liệu là java.util.Date

    int courseId;
    int roomId;
}
