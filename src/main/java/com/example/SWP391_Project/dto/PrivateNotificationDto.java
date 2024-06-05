package com.example.SWP391_Project.model;

import com.example.SWP391_Project.enums.Actor;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivateNotificationDto {

    int id;

    String title;

    String content;

    Date createdAt;

    Date updatedAt;

    String sendTo;

}
