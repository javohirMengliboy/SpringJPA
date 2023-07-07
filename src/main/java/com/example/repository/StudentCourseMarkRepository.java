package com.example.repository;
import com.example.entity.CourseEntity;
import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentCourseMarkRepository extends JpaRepository<StudentCourseMarkEntity, Integer> {

    List<StudentCourseMarkEntity> findByCreatedDateBetweenAndStudent(LocalDateTime from, LocalDateTime to, StudentEntity student);
    List<StudentCourseMarkEntity> findMarkByStudentOrderByCreatedDateDesc(StudentEntity student);
    List<StudentCourseMarkEntity> findMarkByStudentAndCourseOrderByCreatedDateDesc(StudentEntity studentEntity, CourseEntity courseEntity);
    List<StudentCourseMarkEntity> findTop1MarkAndCourseByStudentOrderByCreatedDateDesc(StudentEntity studentEntity);
}
