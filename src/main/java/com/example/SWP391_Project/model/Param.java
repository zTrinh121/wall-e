package com.example.SWP391_Project.model;

public class Param {
    private String date;
    private int courseId;

    public Param(String date, int courseId) {
        this.date = date;
        this.courseId = courseId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
