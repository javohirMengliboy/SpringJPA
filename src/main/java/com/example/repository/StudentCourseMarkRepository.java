package com.example.repository;
import com.example.entity.CourseEntity;
import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import com.example.mapper.StudentMarkCourseNameMapper;
import com.example.mapper.SCMMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer> {


    /** 2. Update */
    @Transactional
    @Modifying
    @Query("update StudentCourseMarkEntity set mark = :mark where id = :id")
    int update2(@Param("id") Integer id, @Param("mark") Double mark);

    /** 3. Get ById */
    @Query("select new com.example.mapper.SCMMapper(id,student.name,course.name, mark, createdDate)from StudentCourseMarkEntity " +
            "where id = :id")
    SCMMapper getById(@Param("id") Integer id);


    /** 4. Get ById With Detail */
    @Query("from StudentCourseMarkEntity where id = :id")
    Optional<StudentCourseMarkEntity> findById2(@Param("id") Integer id);

    @Query("select new com.example.mapper.SCMMapper(id,student.name,course.name, mark, createdDate)from StudentCourseMarkEntity " +
            "where course.id = :id")
    List<SCMMapper> getByCourseId(@Param("id") Integer id);


    /** 5. Delete ById */
    @Transactional
    @Modifying
    @Query("delete from StudentCourseMarkEntity where id = :id")
    int deleteQuery(@Param("id") Integer id);

    /** 6. Get All */
    @Query("select new com.example.mapper.SCMMapper(id,student.name,course.name, mark, createdDate)from StudentCourseMarkEntity ")
    List<SCMMapper> getAll();

    /** 7. Get By Given Date 8. Get By Between Date */
    List<StudentCourseMarkEntity> findByCreatedDateBetweenAndStudent(LocalDateTime from, LocalDateTime to, StudentEntity student);

    @Query("from StudentCourseMarkEntity where createdDate > :from and createdDate < :to and student = :student")
    List<StudentCourseMarkEntity> findByCreatedDateBetweenAndStudent2(@Param("from") LocalDateTime from,
                                                                      @Param("to") LocalDateTime to,
                                                                      @Param("student") StudentEntity student);

    /** 9. Get All Mark By Student */
    List<StudentCourseMarkEntity> findMarkByStudentOrderByCreatedDateDesc(StudentEntity student);

    @Query("select new com.example.mapper.StudentMarkCourseNameMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :id order by scm.createdDate desc")
    List<StudentMarkCourseNameMapper> findMarkByStudentOrderByCreatedDateDesc2(@Param("id") Integer id);


    /** 10. Get All Mark Student By Given Course */
    List<StudentCourseMarkEntity> findMarkByStudentAndCourseOrderByCreatedDateDesc(StudentEntity studentEntity, CourseEntity courseEntity);

    @Query("select new com.example.mapper.StudentMarkCourseNameMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :sid and c.id = :cid order by scm.createdDate desc")
    List<StudentMarkCourseNameMapper> findMarkByStudentAndCourseOrderByCreatedDateDesc2(@Param("sid") Integer sid,
                                                                                        @Param("cid") Integer cid);

    /** 11. Get Last Mark Student */
    StudentCourseMarkEntity findTop1MarkAndCourseByStudentOrderByCreatedDateDesc(StudentEntity studentEntity);

    @Query("select new com.example.mapper.StudentMarkCourseNameMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :id order by scm.createdDate desc limit 1")
    StudentMarkCourseNameMapper findTop1MarkAndCourseByStudentOrderByCreatedDateDesc2(Integer id);

    /** 12. Get Top 3 Mark Student */
    List<StudentCourseMarkEntity> findTop3MarkByStudentOrderByMarkDesc(StudentEntity studentEntity);

    @Query("select new com.example.mapper.StudentMarkCourseNameMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :id order by scm.mark desc limit 3")
    List<StudentMarkCourseNameMapper> findTop3MarkByStudentOrderByMarkDesc2(@Param("id") Integer id);

    /** 13. Get First Mark Student */
    StudentCourseMarkEntity findTop1MarkAndCourseByStudentOrderByCreatedDateAsc(StudentEntity studentEntity);

    @Query("select new com.example.mapper.StudentMarkCourseNameMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :id order by scm.createdDate asc limit 1")
    StudentMarkCourseNameMapper findTop1MarkAndCourseByStudentOrderByCreatedDateAsc2(@Param("id") Integer studentId);

    /** 14. Get First Mark Student By Given Course */
    Optional<StudentCourseMarkEntity> findTopByStudentAndAndCourseOrderByCreatedDate(StudentEntity studentEntity,CourseEntity courseEntity);

    @Query("select new com.example.mapper.StudentMarkCourseNameMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :sid and c.id = :cid order by scm.createdDate asc limit 1")
    StudentMarkCourseNameMapper findTopByStudentAndAndCourseOrderByCreatedDate2(@Param("sid") Integer sid, @Param("cid") Integer cid);

    /** 15. Get Top 1 Mark Student By Give Course */
    Optional<StudentCourseMarkEntity> findTop1ByStudentAndAndCourseOrderByMarkDesc(StudentEntity studentEntity, CourseEntity courseEntity);

    @Query("select new com.example.mapper.StudentMarkCourseNameMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :sid and c.id = :cid order by scm.mark desc limit 1")
    StudentMarkCourseNameMapper findTop1ByStudentAndAndCourseOrderByMarkDesc2(@Param("sid") Integer sid, @Param("cid") Integer cid);

    /** 16. Get Avg Mark Student */
    @Query("select new com.example.mapper.StudentMarkCourseNameMapper(s.name, avg(scm.mark)) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s " +
            "where s.id = :sid group by s.name")
    StudentMarkCourseNameMapper getAvgMarkByStudentId(@Param("sid") Integer studentId);

    /** 17. Get Avg Mark Student By Given Course */
    @Query("select new com.example.mapper.StudentMarkCourseNameMapper(s.name, avg(scm.mark)) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :sid and c.id = :cid " +
            "group by s.name")
    StudentMarkCourseNameMapper getAvgMarkByStudentIdAndGivenCourse(@Param("sid") Integer studentId, @Param("cid") Integer courseId);

    /** 18. Get Student's Count Of Marks Greater Than The Given Mark .*/
    @Query("select count(*) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :sid and scm.mark > :mark " +
            "group by s.name")
    Long getCountMarkByGivenMarkOlder(@Param("sid") Integer sid, @Param("mark") Double mark);


    /** 19. Get Top 1 Mark By Given Course */
    @Query("select new com.example.mapper.StudentMarkCourseNameMapper(scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.course as c " +
            "where c.id = :cid order by scm.mark desc limit 1")
    StudentMarkCourseNameMapper findTopMarkByCourseOrderByDesc(@Param("cid") Integer courseId);

    /** 20. Get Avg Mark By Given Course */
    @Query("select new com.example.mapper.StudentMarkCourseNameMapper(avg(scm.mark), c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.course as c " +
            "where c.id = :cid group by c.name")
    StudentMarkCourseNameMapper getAVgMarkByGivenCourse(@Param("cid") Integer courseId);


    /** 21. Get Count Mark By Given Course */
    @Query("select count(*) from StudentCourseMarkEntity as scm " +
            "inner join scm.course as c where c.id = :cid " +
            "group by c.name")
    Long getCountMarkByGivenCourse(@Param("cid") Integer courseId);

    /** 22. Get Pagination */
    Page<StudentCourseMarkEntity> getAllBy(Pageable pageable);
    @Query("select new com.example.mapper.SCMMapper(id, student.name, course.name, mark, createdDate) from StudentCourseMarkEntity ")
    Page<SCMMapper> getAllBy2(Pageable pageable);

    /** 23. Get Pagination By StudentId */
    Page<StudentCourseMarkEntity> getAllByStudent(StudentEntity studentEntity,Pageable pageable);

    @Query("select new com.example.mapper.SCMMapper(id, student.name, course.name, mark, createdDate) from StudentCourseMarkEntity" +
            " where student.id = :sid")
    Page<SCMMapper> getAllByStudent2(@Param("sid") Integer studentId, Pageable pageable);

    /** 24. Get Pagination By CourseId */
    Page<StudentCourseMarkEntity> getAllByCourse(CourseEntity courseEntity, Pageable pageable);

    @Query("select new com.example.mapper.SCMMapper(id, student.name, course.name, mark, createdDate) from StudentCourseMarkEntity " +
            " where course.id = :cid")
    Page<SCMMapper> getAllByCourse2(@Param("cid") Integer courseId, Pageable pageable);

}
