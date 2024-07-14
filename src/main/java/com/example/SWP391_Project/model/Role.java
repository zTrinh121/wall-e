package com.example.SWP391_Project.model;

import com.example.SWP391_Project.enums.RoleDescription;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "t05_role")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @Column(name = "C05_ROLE_ID")
    int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "C05_ROLE_DESC", nullable = false)
    RoleDescription description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonBackReference
    List<User> users;

    public Role() {
    }

    public Role(int id, RoleDescription description) {
        this.id = id;
        this.description = description;
    }
}
