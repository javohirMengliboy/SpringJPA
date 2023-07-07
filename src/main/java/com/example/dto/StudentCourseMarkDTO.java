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
}
