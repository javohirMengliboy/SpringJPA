package com.example.mapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentMarkCourseNameMapper {
    private String studentName;
    private Double mark;
    private String courseName;

    public StudentMarkCourseNameMapper(String studentName, Double mark, String courseName) {
        this.studentName = studentName;
        this.mark = mark;
        this.courseName = courseName;
    }

    public StudentMarkCourseNameMapper(String studentName, Double mark) {
        this.studentName = studentName;
        this.mark = mark;
    }

    public StudentMarkCourseNameMapper(Double mark, String courseName) {
        this.mark = mark;
        this.courseName = courseName;
    }

    public StudentMarkCourseNameMapper() {
    }
}
