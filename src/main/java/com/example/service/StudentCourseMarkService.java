package com.example.service;

import com.example.dto.StudentCourseMarkDTO;
import com.example.entity.CourseEntity;
import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.*;
import com.example.repository.StudentCourseMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseMarkService {
    @Autowired
    private StudentCourseMarkRepository studentCourseMarkRepository;

    /** 1. Create */
    public StudentCourseMarkDTO create(StudentCourseMarkDTO course) {
        StudentCourseMarkEntity markEntity = new StudentCourseMarkEntity();

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(course.getStudent());
        markEntity.setStudent(studentEntity);

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(course.getCourse());
        markEntity.setCourse(courseEntity);

        markEntity.setMark(course.getMark());

        studentCourseMarkRepository.save(markEntity);
        course.setId(markEntity.getId());
        return course;
    }

    public List<StudentCourseMarkDTO> createAll(List<StudentCourseMarkDTO> list) {
        for (StudentCourseMarkDTO dto : list) {
            StudentCourseMarkEntity entity = new StudentCourseMarkEntity();

            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setId(dto.getStudent());
            entity.setStudent(studentEntity);

            CourseEntity courseEntity = new CourseEntity();
            courseEntity.setId(dto.getCourse());
            entity.setCourse(courseEntity);

            entity.setMark(dto.getMark());

            studentCourseMarkRepository.save(entity);
            dto.setId(courseEntity.getId());
        }
        return list;
    }
    /** 2. Update */
    public Boolean update(Integer id, StudentCourseMarkDTO dto) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        StudentCourseMarkEntity entity = optional.get();
        entity.setMark(dto.getMark());
        studentCourseMarkRepository.save(entity);
        return true;
    }

    public Boolean update2(Integer id, StudentCourseMarkDTO dto) {
        int effectiveRows = studentCourseMarkRepository.update2(id,dto.getMark());
        return effectiveRows != 0;
    }

    /** 3. Get ById */
    public StudentCourseMarkDTO getById(Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        StudentCourseMarkEntity entity = optional.get();
        return toDto(entity);
    }
    public SCMMapper getById2(Integer id) {
        SCMMapper mapper = studentCourseMarkRepository.getById(id);
        if (mapper == null) {
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        return mapper;
    }

    /** 4. Get ById With Detail */
    public StudentCourseMarkMapper getByIdWithDetail(Integer id) {
//        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById2(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        StudentCourseMarkEntity entity = optional.get();
        StudentCourseMarkMapper markMapper = new StudentCourseMarkMapper();

        StudentMapper studentMapper = new StudentMapper();
        studentMapper.setId(entity.getStudent().getId());
        studentMapper.setName(entity.getStudent().getName());
        studentMapper.setSurname(entity.getStudent().getSurname());

        CourseMapper courseMapper = new CourseMapper();
        courseMapper.setId(entity.getCourse().getId());
        courseMapper.setName(entity.getCourse().getName());

        markMapper.setId(entity.getId());
        markMapper.setStudent(studentMapper);
        markMapper.setCourse(courseMapper);
        markMapper.setMark(entity.getMark());
        markMapper.setCreatedDate(entity.getCreatedDate());
        return markMapper;
    }

    /** 5. Delete ById */
    public Boolean delete(Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()){
            return false;
        }
        studentCourseMarkRepository.deleteById(id);
        return true;
    }

    public Boolean delete2(Integer id) {
        int effectiveRows = studentCourseMarkRepository.deleteQuery(id);
        return effectiveRows != 0;
    }

    /** 6. Get All */
    public List<StudentCourseMarkDTO> getAll() {
        Iterable<StudentCourseMarkEntity> iterable = studentCourseMarkRepository.findAll();
        List<StudentCourseMarkDTO> dtoList = new ArrayList<>();
        iterable.forEach(entity -> {
            dtoList.add(toDto(entity));
        });
        return dtoList;
    }
    public List<SCMMapper> getAll2() {
        List<SCMMapper> mappers = studentCourseMarkRepository.getAll();
        if (mappers.isEmpty()){
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        return mappers;
    }

    /** 7. Get By Given Date */
    public List<Double> getMarkByGivenDate(Integer studentId, LocalDate date) {
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);
        List<StudentCourseMarkEntity> studentCourseMarkEntityList =  studentCourseMarkRepository.findByCreatedDateBetweenAndStudent(from, to, studentEntity);
        List<Double> markList = new LinkedList<>();
        studentCourseMarkEntityList.forEach(entity -> markList.add(entity.getMark()));
        return markList;
    }

    /** 8. Get By Between Date */
    public Object getMarkByBetweenDate(Integer studentId, LocalDate from, LocalDate to) {
        LocalDateTime fromDate = LocalDateTime.of(from, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(to, LocalTime.MAX);
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);
        List<StudentCourseMarkEntity> studentCourseMarkEntityList =  studentCourseMarkRepository.findByCreatedDateBetweenAndStudent(fromDate, toDate, studentEntity);
        if (studentCourseMarkEntityList.isEmpty()){
            throw new ItemNotFoundException("Not found");
        }
        List<Double> markList = new LinkedList<>();
        studentCourseMarkEntityList.forEach(entity -> markList.add(entity.getMark()));
        return markList;
    }

    /** 9. Get All Mark By Student */
    public List<Double> getAllMarkByStudentId(Integer studentId) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);
        List<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.findMarkByStudentOrderByCreatedDateDesc(studentEntity);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Not found");
        }
        List<Double> markList = new ArrayList<>();
        entityList.forEach(entity -> markList.add(entity.getMark()));
        return markList;
    }

    public List<StudentMarkCourseNameMapper> getAllMarkByStudentId2(Integer studentId) {
        List<StudentMarkCourseNameMapper> mappers = studentCourseMarkRepository.findMarkByStudentOrderByCreatedDateDesc2(studentId);
        if (mappers.isEmpty()){
            throw new ItemNotFoundException("Not found");
        }
        return mappers;
    }

    /** 10. Get All Mark Student By Given Course */
    public List<Double> getAllMarkByStudentIdAndByCourseId(Integer studentId, Integer courseId) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(courseId);

        List<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.findMarkByStudentAndCourseOrderByCreatedDateDesc(studentEntity, courseEntity);
        List<Double> markList = new ArrayList<>();
        entityList.forEach(entity -> markList.add(entity.getMark()));
        return markList;
    }

    public List<StudentMarkCourseNameMapper> getAllMarkByStudentIdAndByCourseId2(Integer studentId, Integer courseId) {
        List<StudentMarkCourseNameMapper> mappers = studentCourseMarkRepository.findMarkByStudentAndCourseOrderByCreatedDateDesc2(studentId,courseId);
        if (mappers.isEmpty()){
            throw new ItemNotFoundException("Not found");
        }
        return mappers;
    }

    /** 11. Get Last Mark Student */
    public StudentMarkCourseNameMapper getLastMarkAndCourseByStudentId(Integer studentId) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);

        StudentCourseMarkEntity entity = studentCourseMarkRepository.findTop1MarkAndCourseByStudentOrderByCreatedDateDesc(studentEntity);

        StudentMarkCourseNameMapper studentMarkCourseNameMapper = new StudentMarkCourseNameMapper();
        studentMarkCourseNameMapper.setMark(entity.getMark());
        studentMarkCourseNameMapper.setCourseName(entity.getCourse().getName());
        return studentMarkCourseNameMapper;
    }

    public StudentMarkCourseNameMapper getLastMarkAndCourseByStudentId2(Integer studentId) {
        StudentMarkCourseNameMapper mapper = studentCourseMarkRepository.findTop1MarkAndCourseByStudentOrderByCreatedDateDesc2(studentId);
        if (mapper == null){
            throw new ItemNotFoundException("Not found");
        }
        return mapper;
    }

    /** 12. Get Top 3 Mark Student */
    public List<StudentMarkCourseNameMapper> getTop3MarkAndCourseByStudentId(Integer studentId) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);

        List<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.findTop3MarkByStudentOrderByMarkDesc(studentEntity);
        List<StudentMarkCourseNameMapper> markList = new ArrayList<>();
        entityList.forEach(entity -> {
            StudentMarkCourseNameMapper studentMarkCourseNameMapper = new StudentMarkCourseNameMapper();
            studentMarkCourseNameMapper.setMark(entity.getMark());
            studentMarkCourseNameMapper.setCourseName(entity.getCourse().getName());
            markList.add(studentMarkCourseNameMapper);
        });
        return markList;

    }

    public List<StudentMarkCourseNameMapper> getTop3MarkAndCourseByStudentId2(Integer studentId){
        List<StudentMarkCourseNameMapper> mappers = studentCourseMarkRepository.findTop3MarkByStudentOrderByMarkDesc2(studentId);
        if (mappers.isEmpty()){
            throw new ItemNotFoundException("Not found");
        }
        return mappers;
    }

    /** 13. Get First Mark Student */
    public StudentMarkCourseNameMapper getFirstMarkAndCourseByStudentId(Integer studentId) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);

        StudentCourseMarkEntity entity = studentCourseMarkRepository.findTop1MarkAndCourseByStudentOrderByCreatedDateAsc(studentEntity);
        StudentMarkCourseNameMapper lastMarkAndCourseMapper = new StudentMarkCourseNameMapper();
        lastMarkAndCourseMapper.setMark(entity.getMark());
        lastMarkAndCourseMapper.setCourseName(entity.getCourse().getName());

        return lastMarkAndCourseMapper;
    }
    public StudentMarkCourseNameMapper getFirstMarkAndCourseByStudentId2(Integer studentId){
        StudentMarkCourseNameMapper mapper = studentCourseMarkRepository.findTop1MarkAndCourseByStudentOrderByCreatedDateAsc2(studentId);
        if (mapper == null){
            throw new ItemNotFoundException("Not found");
        }
        return mapper;
    }

    /** 14. Get First Mark Student By Given Course */
    public StudentCourseMarkDTO getFirstMarkByStudentIdAndGivenCourse(Integer studentId, Integer courseId) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(courseId);

        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findTopByStudentAndAndCourseOrderByCreatedDate(studentEntity,courseEntity);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Mark not found");
        }
        return toDto(optional.get());
    }

    public StudentMarkCourseNameMapper getFirstMarkByStudentIdAndGivenCourse2(Integer studentId, Integer courseId) {
        StudentMarkCourseNameMapper mapper = studentCourseMarkRepository.findTopByStudentAndAndCourseOrderByCreatedDate2(studentId, courseId);
        if (mapper == null){
            throw new ItemNotFoundException("Not found");
        }
        return mapper;
    }

    /** 15. Get Top 1 Mark Student By Give Course */
    public StudentMarkCourseNameMapper getTop1MarkByStudentIdAndGivenCourse(Integer studentId, Integer courseId) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(courseId);

        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findTop1ByStudentAndAndCourseOrderByMarkDesc(studentEntity,courseEntity);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Mark not found");
        }
        StudentCourseMarkEntity entity = optional.get();
        StudentMarkCourseNameMapper mapper = new StudentMarkCourseNameMapper();
        mapper.setCourseName(entity.getCourse().getName());
        mapper.setMark(entity.getMark());
        mapper.setStudentName(entity.getStudent().getName());
        return mapper;
    }

    public StudentMarkCourseNameMapper getTop1MarkByStudentIdAndGivenCourse2(Integer studentId, Integer courseId) {
        StudentMarkCourseNameMapper mapper = studentCourseMarkRepository.findTop1ByStudentAndAndCourseOrderByMarkDesc2(studentId, courseId);
        if (mapper == null){
            throw new ItemNotFoundException("Not found");
        }
        return mapper;
    }

    /** 16. Get Avg Mark Student */
    public StudentMarkCourseNameMapper getAvgMarkByStudentId(Integer studentId) {
        StudentMarkCourseNameMapper mapper = studentCourseMarkRepository.getAvgMarkByStudentId(studentId);
        if (mapper == null){
            throw new ItemNotFoundException("Not found");
        }
        return mapper;
    }

    /** 17. Get Avg Mark Student By Given Course */
    public StudentMarkCourseNameMapper getAvgMarkByStudentIdAndGivenCourse(Integer studentId, Integer courseId) {
        StudentMarkCourseNameMapper mapper = studentCourseMarkRepository.getAvgMarkByStudentIdAndGivenCourse(studentId,courseId);
        if (mapper == null){
            throw new ItemNotFoundException("Not found");
        }
        return mapper;
    }

    /** 18. Get Student's Count Of Marks Greater Than The Given Mark .*/
    public Long getCountMarkByGivenMarkOlder(Integer studentId, Double mark) {
        Long count = studentCourseMarkRepository.getCountMarkByGivenMarkOlder(studentId, mark);
        if (count == null){
            throw new ItemNotFoundException("Not found");
        }
        return count;
    }

    /** 19. Get Top 1 Mark By Given Course */
    public StudentMarkCourseNameMapper getTopMarkByGivenCourse(Integer courseId) {
        StudentMarkCourseNameMapper mapper = studentCourseMarkRepository.findTopMarkByCourseOrderByDesc(courseId);
        if (mapper == null){
            throw new ItemNotFoundException("Not found");
        }
        return mapper;
    }

    /** 20. Get Avg Mark By Given Course */
    public StudentMarkCourseNameMapper getAVgMarkByGivenCourse(Integer courseId) {
        StudentMarkCourseNameMapper mapper = studentCourseMarkRepository.getAVgMarkByGivenCourse(courseId);
        if (mapper == null){
            throw new ItemNotFoundException("Not found");
        }
        return mapper;
    }

    /** 21. Get Count Mark By Given Course */
    public Long getCountMarkByGivenCourse(Integer courseId) {
        Long count = studentCourseMarkRepository.getCountMarkByGivenCourse(courseId);
        if (count == null){
            throw new ItemNotFoundException("Not found");
        }
        return count;
    }

    /** 22. Get Pagination */
    public Page<SCMMapper> getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<SCMMapper> dtoList = studentCourseMarkRepository.getAllBy2(pageable);
        if (dtoList == null){
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        return dtoList;
    }

    public Page<StudentCourseMarkEntity> getPagination2(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<StudentCourseMarkEntity> dtoList = studentCourseMarkRepository.getAllBy(pageable);
        if (dtoList == null){
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        return dtoList;
    }

    /** 23. Get Pagination By StudentId */
    public Page<StudentCourseMarkEntity> getPaginationByStudentId2(Integer studentId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);
        Page<StudentCourseMarkEntity> dtoList = studentCourseMarkRepository.getAllByStudent(studentEntity, pageable);
        if (dtoList == null){
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        return dtoList;
    }

    public Page<SCMMapper> getPaginationByStudentId(Integer studentId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<SCMMapper> dtoList = studentCourseMarkRepository.getAllByStudent2(studentId, pageable);
        if (dtoList == null){
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        return dtoList;
    }

    /** 24. Get Pagination By CourseId */
    public Page<StudentCourseMarkEntity> getPaginationByCourseId(Integer courseId,int page, int size) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(courseId);
        Pageable pageable = PageRequest.of(page,size);
        Page<StudentCourseMarkEntity> dtoList = studentCourseMarkRepository.getAllByCourse(courseEntity,pageable);
        if (dtoList == null){
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        return dtoList;
    }

    public Page<SCMMapper> getPaginationByCourseId2(Integer courseId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<SCMMapper> dtoList = studentCourseMarkRepository.getAllByCourse2(courseId, pageable);
        if (dtoList == null){
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        return dtoList;
    }

    private StudentCourseMarkDTO toDto(StudentCourseMarkEntity entity) {
        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
        dto.setId(entity.getId());
        dto.setStudent(entity.getStudent().getId());
        dto.setCourse(entity.getCourse().getId());
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}

