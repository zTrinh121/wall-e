package com.example.SWP391_Project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "t07_material")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C07_MATERIALS_ID")
    int id;

    @Column(name = "C07_MATERIALS_NAME", nullable = false, length = 100)
    String materialsName;

    @Column(name = "T07_SUBJECT_NAME", nullable = false, length = 255)
    String subjectName;

    @Column(name = "C07_PDF_PATH")
    String pdfPath;

    @Column(name = "C07_CLOUDINARY_PDF_ID")
    String cloudinaryPdfId;

    @ManyToOne
    @JoinColumn(name = "C07_TEACHER_ID", referencedColumnName = "C14_USER_ID")
    @JsonManagedReference
    User teacher;

}
