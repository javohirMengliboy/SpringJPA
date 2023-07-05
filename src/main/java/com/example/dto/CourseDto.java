package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CourseDto {
    private Integer id;
    private String name;
    private Double price;
    private Integer duration;
    private LocalDateTime createdDate;

    public CourseDto() {
    }

    public CourseDto(Integer id, String name, Double price, Integer duration, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.createdDate = createdDate;
    }
}
