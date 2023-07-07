package com.example.service;

import com.example.dto.StudentCourseMarkDTO;
import com.example.entity.CourseEntity;
import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.CourseMapper;
import com.example.mapper.LastMarkAndCourseMapper;
import com.example.mapper.StudentCourseMarkMapper;
import com.example.mapper.StudentMapper;
import com.example.repository.StudentCourseMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public StudentCourseMarkDTO add(StudentCourseMarkDTO course) {
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

    public List<StudentCourseMarkDTO> addAll(List<StudentCourseMarkDTO> list) {
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

    public List<StudentCourseMarkDTO> getAll() {
        Iterable<StudentCourseMarkEntity> iterable = studentCourseMarkRepository.findAll();
        List<StudentCourseMarkDTO> dtoList = new ArrayList<>();
        iterable.forEach(entity -> {
            dtoList.add(toDto(entity));
        });
        return dtoList;
    }

    public StudentCourseMarkDTO getById(Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Course not found");
        }
        StudentCourseMarkEntity entity = optional.get();
        return toDto(entity);
    }

    public StudentCourseMarkMapper getByIdWithDetail(Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Course not found");
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





    public Boolean delete(Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()){
            return false;
        }
        studentCourseMarkRepository.deleteById(id);
        return true;
    }

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


    private StudentCourseMarkDTO toDto(StudentCourseMarkEntity entity) {
        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
        dto.setId(entity.getId());
        dto.setStudent(entity.getStudent().getId());
        dto.setCourse(entity.getCourse().getId());
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

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

    public Object getMarkByBetweenDate(Integer studentId, LocalDate from, LocalDate to) {
        LocalDateTime fromDate = LocalDateTime.of(from, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(to, LocalTime.MAX);
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);
        List<StudentCourseMarkEntity> studentCourseMarkEntityList =  studentCourseMarkRepository.findByCreatedDateBetweenAndStudent(fromDate, toDate, studentEntity);
        List<Double> markList = new LinkedList<>();
        studentCourseMarkEntityList.forEach(entity -> markList.add(entity.getMark()));
        return markList;
    }

    public List<Double> getAllMarkByStudentId(Integer studentId) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);
        List<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.findMarkByStudentOrderByCreatedDateDesc(studentEntity);
        List<Double> markList = new ArrayList<>();
        entityList.forEach(entity -> markList.add(entity.getMark()));
        return markList;
    }

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

    public List<LastMarkAndCourseMapper> getMarkAndCourseByStudentId(Integer studentId) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentId);

        List<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.findTop1MarkAndCourseByStudentOrderByCreatedDateDesc(studentEntity);
        List<LastMarkAndCourseMapper> markList = new ArrayList<>();
        entityList.forEach(entity -> {
            LastMarkAndCourseMapper lastMarkAndCourseMapper = new LastMarkAndCourseMapper();
            lastMarkAndCourseMapper.setMark(entity.getMark());
            lastMarkAndCourseMapper.setCourseName(entity.getCourse().getName());
            markList.add(lastMarkAndCourseMapper);
        });
        return markList;
    }
}

