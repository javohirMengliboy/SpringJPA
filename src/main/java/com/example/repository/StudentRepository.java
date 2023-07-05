package com.example.repository;

import com.example.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {
    Optional<StudentEntity> getByPhone(String phone);

    List<StudentEntity> getByName(String name);

    Iterable<StudentEntity> getBySurname(String surname);

    Iterable<StudentEntity> getByLevel(Integer level);

    Iterable<StudentEntity> getByGender(String gender);
    List<StudentEntity> getStudentEntitiesByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
