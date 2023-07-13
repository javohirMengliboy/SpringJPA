package com.example.dto.filter;

import com.example.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class StudentFilterDTO {
    private String name;
    private String surname;
    private Integer level;
    private Integer age;
    private Gender gender;
    private String phone;
    private LocalDate createdDate;
}
