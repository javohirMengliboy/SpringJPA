package com.example.dto.filter;

import com.example.entity.StudentEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class StudentCourseMarkFilterDTO {
    private Integer id;
    private Integer student;
    private Integer course;
    private Double mark;
    private LocalDate createdDate;

}
