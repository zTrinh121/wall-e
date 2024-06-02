package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "t06_role")
public class Role {
    @Id
    @Column(name = "C06_ROLE_ID")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "C06_ROLE_DESC", nullable = false)
    private RoleDescription description;

    public enum RoleDescription {
        STUDENT,
        PARENT,
        TEACHER,
        MANAGER,
        ADMIN
    }

    // Constructor, getters, and setters

    public Role() {}

    public Role(int id, RoleDescription description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleDescription getDescription() {
        return description;
    }

    public void setDescription(RoleDescription description) {
        this.description = description;
    }
}
