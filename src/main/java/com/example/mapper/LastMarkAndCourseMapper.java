package com.example.mapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LastMarkAndCourseMapper {
    private String studentName;
    private Double mark;
    private String courseName;

    public LastMarkAndCourseMapper(String studentName, Double mark, String courseName) {
        this.studentName = studentName;
        this.mark = mark;
        this.courseName = courseName;
    }

    public LastMarkAndCourseMapper(String studentName, Double mark) {
        this.studentName = studentName;
        this.mark = mark;
    }

    public LastMarkAndCourseMapper(Double mark, String courseName) {
        this.mark = mark;
        this.courseName = courseName;
    }

    public LastMarkAndCourseMapper() {
    }
}
