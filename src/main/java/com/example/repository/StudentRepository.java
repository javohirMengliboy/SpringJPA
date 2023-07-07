package com.example.repository;

import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import org.springframework.data.domain.Pageable;
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

    List<StudentEntity> findAllBy(Pageable pageable);
    List<StudentEntity> findAllByLevelOrderByIdAsc(Pageable pageable, int level);

    List<StudentEntity> findAllByGenderOrderById(Gender gender, Pageable pageable);
//    List<StudentEntity> findAllByGenderOrderById(Pageable pageable, String gender);
}
