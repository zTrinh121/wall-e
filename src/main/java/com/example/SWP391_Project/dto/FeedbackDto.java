package com.example.SWP391_Project.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackDto {

    String description;

    int rating;

    int sendToUser_Id;
}
