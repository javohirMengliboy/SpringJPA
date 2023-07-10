package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class StudentCourseMarkDTO {
    private Integer id;
    private Integer student;
    private Integer course;
    private Double mark;
    private LocalDateTime createdDate;

    public StudentCourseMarkDTO() {
    }

    public StudentCourseMarkDTO(Integer id, Integer student, Integer course, Double mark, LocalDateTime createdDate) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.mark = mark;
        this.createdDate = createdDate;
    }
}
