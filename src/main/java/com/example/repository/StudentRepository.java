package com.example.repository;

import com.example.dto.StudentDTO;
import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {
    /** 2. Get All Student */
    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity ")
    List<StudentDTO> getAllStudent();

    /** 3. Get Student ById */
    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity " +
            "where id = :id")
    StudentDTO getStudentById(@Param("id") Integer id);

    /** 4. Update ById */
    @Transactional
    @Modifying
    @Query("update StudentEntity set name = :name where id = :id")
    int update2(@Param("name") String name, @Param("id") Integer id);

    /** 5 Delete ById */
    @Transactional
    @Modifying
    @Query("delete from StudentEntity where id = :id")
    int delete2(@Param("id") Integer id);

    /** 6. Get By Each Field */
    Optional<StudentEntity> getByPhone(String phone);

    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity " +
            "where phone = :phone")
    List<StudentDTO> getByPhone2(@Param("phone") String phone);

        /** By Name*/
    List<StudentEntity> getByName(String name);

    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity " +
            "where name = :name")
    List<StudentDTO> getByName2(@Param("name") String name);

        /** By Surname */
    List<StudentEntity> getBySurname(String surname);

    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity " +
            "where surname = :surname")
    List<StudentDTO> getBySurname2(@Param("surname") String surname);

        /** By Level */
    List<StudentEntity> getByLevel(Integer level);

    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity " +
            "where level = :level")
    List<StudentDTO> getByLevel2(@Param("level") Integer level);

        /** By Age */
    List<StudentEntity> getByAge(Integer age);

    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity " +
            "where age = :age")
    List<StudentDTO> getByAge2(@Param("age") Integer age);

        /** By Gender */
    List<StudentEntity> getByGender(Gender gender);

    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity " +
            "where gender = :gender")
    List<StudentDTO> getByGender2(@Param("gender") Gender gender);

    /** 7. Get By Given Date , 8. Get By Between Date */
    List<StudentEntity> getStudentEntitiesByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity " +
            "where createdDate > :from and createdDate < :to")
    List<StudentDTO> getStudentEntitiesByCreatedDateBetween2(@Param("from") LocalDateTime from,
                                                             @Param("to") LocalDateTime to);


    /** 9. Get Pagination */
    List<StudentEntity> findAllBy(Pageable pageable);

    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity")
    List<StudentDTO> findAllBy2(Pageable pageable);


    /** 10. Get Pagination By Level */
    List<StudentEntity> findAllByLevelOrderByIdAsc(Pageable pageable, int level);

    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity " +
            "where level = :level order by id")
    List<StudentDTO> findAllByLevelOrderByIdAsc2(@Param("level") Integer level, Pageable pageable);

    /** 11. Get Pagination By Gender */
    List<StudentEntity> findAllByGenderOrderById(Gender gender, Pageable pageable);

    @Query("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity " +
            "where gender = :gender order by createdDate")
    List<StudentDTO> findAllByGenderOrderById2(@Param("gender") Gender gender, Pageable pageable);

}
