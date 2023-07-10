package com.example.mapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SCMMapper {
    private Integer id;
    private String studentName;
    private String courseName;
    private Double mark;
    private LocalDateTime createdDate;

    public SCMMapper() {
    }

    public SCMMapper(Integer id, String studentName, String courseName, Double mark, LocalDateTime createdDate) {
        this.id = id;
        this.studentName = studentName;
        this.courseName = courseName;
        this.mark = mark;
        this.createdDate = createdDate;
    }
}
