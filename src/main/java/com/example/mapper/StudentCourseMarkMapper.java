package com.example.mapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class StudentCourseMarkMapper {
    private Integer id;
    private StudentMapper student;
    private CourseMapper course;
    private Double mark;
    private LocalDateTime createdDate;
}
