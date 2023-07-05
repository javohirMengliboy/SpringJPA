package com.example.service;

import com.example.dto.StudentDto;
import com.example.entity.StudentEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentDto add(StudentDto dto){
        check(dto);
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(dto.getName());
        studentEntity.setSurname(dto.getSurname());
        studentEntity.setLevel(dto.getLevel());
        studentEntity.setPhone(dto.getPhone());
        studentEntity.setGender(dto.getGender());
        studentEntity.setAge(dto.getAge());
        studentEntity.setCreatedDate(LocalDateTime.now());
        studentRepository.save(studentEntity);
        dto.setId(studentEntity.getId());
        return dto;
    }

    public List<StudentDto> addAll(List<StudentDto> list){
        for (StudentDto dto : list) {
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setName(dto.getName());
            studentEntity.setSurname(dto.getSurname());
            studentEntity.setPhone(dto.getPhone());
            studentEntity.setLevel(dto.getLevel());
            studentEntity.setGender(dto.getGender());
            studentEntity.setAge(dto.getAge());
            studentEntity.setCreatedDate(LocalDateTime.now());
            studentRepository.save(studentEntity);
            dto.setId(studentEntity.getId());
        }
        return list;
    }

    /** Get Methods */

    public List<StudentDto> getAll(){
        Iterable<StudentEntity> iterable = studentRepository.findAll();
        List<StudentDto> dtoList = new ArrayList<>();
        iterable.forEach(studentEntity -> {
            dtoList.add(toDto(studentEntity));
        });
        return dtoList;
    }

    public StudentDto getById(Integer id){
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        StudentEntity entity = optional.get();
        return toDto(entity);
    }

    public Object getByPhone(String phone) {
        Optional<StudentEntity> optional = studentRepository.getByPhone(phone);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }

        StudentEntity entity = optional.get();
        return toDto(entity);
    }

    public List<StudentDto> getByName(String name){
        List<StudentEntity> entityList = studentRepository.getByName(name);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        List<StudentDto> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
        return dtoList;
    }

    public List<StudentDto> getBySurname(String surname){
        Iterable<StudentEntity> iterable = studentRepository.getBySurname(surname);
        List<StudentDto> dtoList = new ArrayList<>();
        iterable.forEach((entity) -> dtoList.add(toDto(entity)));
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }
    public List<StudentDto> getByLevel(Integer level){
        Iterable<StudentEntity> iterable = studentRepository.getByLevel(level);
        List<StudentDto> dtoList = new ArrayList<>();
        iterable.forEach((entity) -> dtoList.add(toDto(entity)));
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

    public List<StudentDto> getByGender(String gender) {
        Iterable<StudentEntity> iterable = studentRepository.getByGender(gender);
        List<StudentDto> dtoList = new ArrayList<>();
        iterable.forEach((entity) -> dtoList.add(toDto(entity)));
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

    public List<StudentDto> getByCreatedDate(LocalDate createdDate) {
        List<StudentEntity> studentEntities = studentRepository.getStudentEntitiesByCreatedDateBetween(createdDate.atStartOfDay(),createdDate.plusDays(1).atStartOfDay());
        if (studentEntities.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        List<StudentDto> dtoList = new ArrayList<>();
        for (StudentEntity entity : studentEntities) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    public List<StudentEntity> getStudentsBetweenDates(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.plusDays(1).atStartOfDay().minusNanos(1);
        return studentRepository.getStudentEntitiesByCreatedDateBetween(startOfDay, endOfDay);
    }
    public void deleteAll() {
        studentRepository.deleteAll();
    }

    public Boolean delete(Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()){
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    public Boolean update(Integer id, StudentDto student) {
        check(student);
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()){
            return false;
        }
        StudentEntity entity = optional.get();
        entity.setName(student.getName());
        entity.setSurname(student.getSurname());
        studentRepository.save(entity);
        return true;
    }



    private StudentDto toDto(StudentEntity studentEntity) {
        StudentDto dto = new StudentDto();
        dto.setId(studentEntity.getId());
        dto.setName(studentEntity.getName());
        dto.setSurname(studentEntity.getSurname());
        dto.setLevel(studentEntity.getLevel());
        dto.setAge(studentEntity.getAge());
        dto.setGender(studentEntity.getGender());
        dto.setPhone(studentEntity.getPhone());
        dto.setCreatedDate(studentEntity.getCreatedDate());
        return dto;
    }

    private void check(StudentDto dto) {
        if (dto.getName() == null || dto.getName().isBlank()){
            throw  new AppBadRequestException("Where Name?");
        }
        if (dto.getSurname() == null || dto.getSurname().isBlank()){
            throw  new AppBadRequestException("Where Surname?");
        }
    }



}
