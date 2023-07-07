package com.example.dto;

import com.example.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class StudentDTO {
    private Integer id;
    private String name;
    private String surname;
    private Integer level;
    private Integer age;
    private Gender gender;
    private String phone;
    private LocalDateTime createdDate;

    public StudentDTO() {
    }

    public StudentDTO(Integer id, String name, String surname, Integer level, Integer age, Gender gender, String phone, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.level = level;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.createdDate = createdDate;
    }
}
