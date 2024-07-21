package com.example.SWP391_Project.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SlotsDto {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
//    private int courseId;
//    private int roomId;
}
