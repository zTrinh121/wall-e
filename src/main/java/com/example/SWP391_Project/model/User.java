package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t14_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "ScheduleMapping",
                columns = {
                        @ColumnResult(name = "courseId"),
                        @ColumnResult(name = "courseDate"),
                        @ColumnResult(name = "startTime"),
                        @ColumnResult(name = "endTime"),
                        @ColumnResult(name = "studentId")
                }
        )
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C14_USER_ID")
    int id;

    @Column(name = "C14_USERNAME", nullable = false)
    String username;

    @Column(name = "C14_USER_PASSWORD", nullable = false)
    String password;

    @Column(name = "C14_ACC_STATUS", nullable = false)
    boolean status;

    @Column(name = "C14_USER_CODE", nullable = false)
    String code;

    @Column(name = "C14_NAME", nullable = true)
    String name;

    @Column(name = "C14_USER_PHONE", nullable = false)
    String phone;

    @Column(name = "C14_USER_ADDRESS", nullable = true)
    String address;

    @Column(name = "C14_USER_DOB", columnDefinition = "DATE", nullable = true)
    Date dob;

    @Column(name = "C14_USER_GENDER", nullable = true)
    boolean gender;

    @Column(name = "C14_USER_EMAIL", nullable = false)
    String email;

    @Column(name = "C14_VERIFICATION_CODE", nullable = false)
    String verificationCode;

    @Column(name = "C14_PROFILE_IMAGE")
    String profileImage;

    @Column(name = "C14_CLOUDINARY_IMAGE_ID")
    String cloudinaryImageId;

    @ManyToOne
    @JoinColumn(name = "C14_ROLE_ID")
    Role role;

    @ManyToOne
    @JoinColumn(name = "C14_PARENT_ID", referencedColumnName = "C14_USER_ID")
    User parent;

    @OneToMany(mappedBy = "student")
    @JsonBackReference
    List<Enrollment> enrollments;

}