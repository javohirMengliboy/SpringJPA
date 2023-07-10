package com.example.repository;
import com.example.dto.StudentCourseMarkDTO;
import com.example.entity.CourseEntity;
import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import com.example.mapper.LastMarkAndCourseMapper;
import com.example.mapper.SCMMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer> {


    /** 2 */
    @Transactional
    @Query("update from StudentCourseMarkEntity set mark = :mark where id = :id")
    int updateMarkById(@Param("mark") Double mark, @Param("id") Integer id);
    /** 3 */
    @Query("select new com.example.mapper.SCMMapper(id,student.name,course.name, mark, createdDate)from StudentCourseMarkEntity " +
            "where id = :id")
    List<SCMMapper> getById(@Param("id") Integer id);


    /** 4 */

    @Query("select new com.example.mapper.SCMMapper(id,student.name,course.name, mark, createdDate)from StudentCourseMarkEntity " +
            "where student.id = :id")
    List<SCMMapper> getByStudentId(@Param("id") Integer id);

    @Query("select new com.example.mapper.SCMMapper(id,student.name,course.name, mark, createdDate)from StudentCourseMarkEntity " +
            "where course.id = :id")
    List<SCMMapper> getByCourseId(@Param("id") Integer id);


    /** 5 */
    @Transactional
    @Query("delete from StudentCourseMarkEntity where id = :id")
    int deleteQuery(@Param("id") Integer id);

    /** 6 */
    @Query("select new com.example.mapper.SCMMapper(id,student.name,course.name, mark, createdDate)from StudentCourseMarkEntity ")
    List<SCMMapper> getAll();

    /** 7,8 */
    List<StudentCourseMarkEntity> findByCreatedDateBetweenAndStudent(LocalDateTime from, LocalDateTime to, StudentEntity student);

    @Query("from StudentCourseMarkEntity where createdDate > :from and createdDate < :to and student = :student")
    List<StudentCourseMarkEntity> findByCreatedDateBetweenAndStudent2(@Param("from") LocalDateTime from,
                                                                      @Param("to") LocalDateTime to,
                                                                      @Param("student") StudentEntity student);

    /** 9 */
    List<StudentCourseMarkEntity> findMarkByStudentOrderByCreatedDateDesc(StudentEntity student);

    @Query("select new com.example.mapper.LastMarkAndCourseMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :id order by scm.createdDate desc")
    List<LastMarkAndCourseMapper> findMarkByStudentOrderByCreatedDateDesc2(@Param("id") Integer id);


    /** 10 */
    List<StudentCourseMarkEntity> findMarkByStudentAndCourseOrderByCreatedDateDesc(StudentEntity studentEntity, CourseEntity courseEntity);

    @Query("select new com.example.mapper.LastMarkAndCourseMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :sid and c.id = :cid order by scm.createdDate desc")
    List<LastMarkAndCourseMapper> findMarkByStudentAndCourseOrderByCreatedDateDesc2(@Param("sid") Integer sid,
                                                                                    @Param("cid") Integer cid);

    /** 11 */
    List<StudentCourseMarkEntity> findTop1MarkAndCourseByStudentOrderByCreatedDateDesc(StudentEntity studentEntity);

    @Query("select new com.example.mapper.LastMarkAndCourseMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :id order by scm.createdDate desc limit 1")
    List<LastMarkAndCourseMapper> findTop1MarkAndCourseByStudentOrderByCreatedDateDesc2(Integer id);

    /** 12 */
    List<StudentCourseMarkEntity> findTop3MarkByStudentOrderByMarkDesc(StudentEntity studentEntity);

    @Query("select new com.example.mapper.LastMarkAndCourseMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :id order by scm.mark desc limit 3")
    List<LastMarkAndCourseMapper> findTop3MarkByStudentOrderByMarkDesc2(@Param("id") Integer id);

    /** 13 */
    List<StudentCourseMarkEntity> findTop1MarkAndCourseByStudentOrderByCreatedDateAsc(StudentEntity studentEntity);

    @Query("select new com.example.mapper.LastMarkAndCourseMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :id order by scm.createdDate asc limit 1")
    LastMarkAndCourseMapper findTop1MarkAndCourseByStudentOrderByCreatedDateAsc2(@Param("id") Integer studentId);

    /** 14 */
    Optional<StudentCourseMarkEntity> findTopByStudentAndAndCourseOrderByCreatedDate(StudentEntity studentEntity,CourseEntity courseEntity);

    @Query("select new com.example.mapper.LastMarkAndCourseMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :sid and c.id = :cid order by scm.createdDate asc limit 1")
    LastMarkAndCourseMapper findTopByStudentAndAndCourseOrderByCreatedDate2(@Param("sid") Integer sid, @Param("cid") Integer cid);

    /** 15 */
    Optional<StudentCourseMarkEntity> findTop1ByStudentAndAndCourseOrderByMarkDesc(StudentEntity studentEntity, CourseEntity courseEntity);

    @Query("select new com.example.mapper.LastMarkAndCourseMapper(s.name, scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :sid and c.id = :cid order by scm.mark desc limit 1")
    LastMarkAndCourseMapper findTop1ByStudentAndAndCourseOrderByMarkDesc2(@Param("sid") Integer sid, @Param("cid") Integer cid);

    /** 16 */
    @Query("select new com.example.mapper.LastMarkAndCourseMapper(s.name, avg(scm.mark)) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s " +
            "where s.id = :sid group by s.name")
    LastMarkAndCourseMapper getAvgMarkByStudentId(@Param("sid") Integer studentId);

    /** 17 */
    @Query("select new com.example.mapper.LastMarkAndCourseMapper(s.name, avg(scm.mark)) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :sid and c.id = :cid " +
            "group by s.name")
    LastMarkAndCourseMapper getAvgMarkByStudentIdAndGivenCourse(@Param("sid") Integer studentId, @Param("cid") Integer courseId);

    /** 18 */
    @Query("select count(*) from StudentCourseMarkEntity as scm " +
            "inner join scm.student as s inner join scm.course as c where s.id = :sid and scm.mark > :mark " +
            "group by s.name")
    Long getCountMarkByGivenMarkOlder(@Param("sid") Integer sid, @Param("mark") Double mark);


    /** 19 */
    @Query("select new com.example.mapper.LastMarkAndCourseMapper(scm.mark, c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.course as c " +
            "where c.id = :cid order by scm.mark desc limit 1")
    LastMarkAndCourseMapper findTopMarkByCourseOrderByDesc(@Param("cid") Integer courseId);

    /** 20 */
    @Query("select new com.example.mapper.LastMarkAndCourseMapper(avg(scm.mark), c.name) from StudentCourseMarkEntity as scm " +
            "inner join scm.course as c " +
            "where c.id = :cid group by c.name")
    LastMarkAndCourseMapper getAVgMarkByGivenCourse(@Param("cid") Integer courseId);


    /** 21 */
    @Query("select count(*) from StudentCourseMarkEntity as scm " +
            "inner join scm.course as c where c.id = :cid " +
            "group by c.name")
    Long getCountMarkByGivenCourse(@Param("cid") Integer courseId);

    /** 22 */
    @Query("select new com.example.mapper.SCMMapper(id, student.name, course.name, mark, createdDate) from StudentCourseMarkEntity ")
    Page<SCMMapper> getPagination(Pageable pageable);

    /** 23 */
    @Query("select new com.example.mapper.SCMMapper(id, student.name, course.name, mark, createdDate) from StudentCourseMarkEntity " +
            " where student.id = :sid")
    Page<SCMMapper> getPaginationByStudentId(@Param("sid") Integer studentId, Pageable pageable);

    /** 24 */
    @Query("select new com.example.mapper.SCMMapper(id, student.name, course.name, mark, createdDate) from StudentCourseMarkEntity " +
            " where course.id = :cid")
    Page<SCMMapper> getPaginationByCourseId(@Param("cid") Integer courseId, Pageable pageable);


}
