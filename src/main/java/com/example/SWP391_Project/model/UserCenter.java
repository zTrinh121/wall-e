package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "t16_user_center",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"C16_USER_ID", "C16_CENTER_ID"},
                             name = "CK_T16_FK")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class UserCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C16_USER_CENTER_ID")
    int id;

    @ManyToOne
    @JoinColumn(name = "C16_USER_ID")
    @JsonManagedReference
    User user;

    @ManyToOne
    @JoinColumn(name = "C16_CENTER_ID")
    @JsonManagedReference
    Center center;

}
