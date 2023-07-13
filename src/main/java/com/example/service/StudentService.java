package com.example.service;

import com.example.dto.CourseDTO;
import com.example.dto.StudentDTO;
import com.example.dto.filter.CourseFilterDTO;
import com.example.dto.filter.StudentFilterDTO;
import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.StudentRepository;
import com.example.repository.custom.StudentCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentCustomRepository studentCustomRepository;

    /** 1. Create Student */
    public StudentDTO create(StudentDTO dto){
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

    public List<StudentDTO> createAll(List<StudentDTO> list){
        for (StudentDTO dto : list) {
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


    /** 2. Get All Student */
    public List<StudentDTO> getAll(){
        Iterable<StudentEntity> iterable = studentRepository.findAll();
        List<StudentDTO> dtoList = new ArrayList<>();
        iterable.forEach(studentEntity -> {
            dtoList.add(toDto(studentEntity));
        });
        return dtoList;
    }

    public List<StudentDTO> getAll2(){
        List<StudentDTO> dtoList = studentRepository.getAllStudent();
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

    /** 3. Get ById */
    public StudentDTO getById(Integer id){
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        StudentEntity entity = optional.get();
        return toDto(entity);
    }

    public StudentDTO getById2(Integer id){
        StudentDTO dto = studentRepository.getStudentById(id);
        if (dto == null){
            throw new ItemNotFoundException("Student not found");
        }
        return dto;
    }

    /** 4. Update ById */
    public Boolean update(Integer id, StudentDTO student) {
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
    public Boolean update2(String name, Integer id) {
        int effectiveRows = studentRepository.update2(name,id);
        return effectiveRows != 0;
    }

    /** 5 Delete ById */
    public Boolean delete(Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()){
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    public Boolean deleteStudentById(Integer id) {
        int effectiveRows = studentRepository.delete2(id);
        return effectiveRows != 0;
    }

    /** 6. Get By Each Field */
    public StudentDTO getByPhone(String phone) {
        Optional<StudentEntity> optional = studentRepository.getByPhone(phone);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }

        StudentEntity entity = optional.get();
        return toDto(entity);
    }
    public List<StudentDTO> getByPhone2(String phone){
        List<StudentDTO> dtoList = studentRepository.getByPhone2(phone);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }
        /** By Name*/
    public List<StudentDTO> getByName(String name){
        List<StudentEntity> entityList = studentRepository.getByName(name);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        List<StudentDTO> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
        return dtoList;
    }

    public List<StudentDTO> getByName2(String name){
        List<StudentDTO> dtoList = studentRepository.getByName2(name);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

        /** By Surname */
    public List<StudentDTO> getBySurname(String surname){
        List<StudentEntity> entityList = studentRepository.getBySurname(surname);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        List<StudentDTO> dtoList = new ArrayList<>();
        entityList.forEach((entity) -> dtoList.add(toDto(entity)));
        return dtoList;
    }
    public List<StudentDTO> getBySurname2(String surname){
        List<StudentDTO> dtoList = studentRepository.getBySurname2(surname);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

        /** By Level */
    public List<StudentDTO> getByLevel(Integer level){
        List<StudentEntity> entityList = studentRepository.getByLevel(level);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        List<StudentDTO> dtoList = new ArrayList<>();
        entityList.forEach((entity) -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    public List<StudentDTO> getByLevel2(Integer level){
        List<StudentDTO> dtoList = studentRepository.getByLevel2(level);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }
        /** By Age */
    public List<StudentDTO> getByAge(Integer age) {
        List<StudentEntity> entityList = studentRepository.getByAge(age);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        List<StudentDTO> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
        return dtoList;
    }

    public List<StudentDTO> getByAge2(Integer age){
        List<StudentDTO> dtoList = studentRepository.getByAge2(age);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

        /** By Gender */
    public List<StudentDTO> getByGender(Gender gender) {
        List<StudentEntity> entityList = studentRepository.getByGender(gender);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        List<StudentDTO> dtoList = new ArrayList<>();
        entityList.forEach((entity) -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    public List<StudentDTO> getByGender2(Gender gender){
        List<StudentDTO> dtoList = studentRepository.getByGender2(gender);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

    /** 7. Get By Given Date */
    public List<StudentDTO> getByGivenDate(LocalDate date) {
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);
        List<StudentEntity> studentEntities = studentRepository.getStudentEntitiesByCreatedDateBetween(from,to);
        if (studentEntities.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        List<StudentDTO> dtoList = new ArrayList<>();
        for (StudentEntity entity : studentEntities) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    public List<StudentDTO> getByGivenDate2(LocalDate date){
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);
        List<StudentDTO> dtoList = studentRepository.getStudentEntitiesByCreatedDateBetween2(from, to);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

    /** 8. Get By Between Date */
    public List<StudentEntity> getByBetweenDates(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.plusDays(1).atStartOfDay().minusNanos(1);
        return studentRepository.getStudentEntitiesByCreatedDateBetween(startOfDay, endOfDay);
    }

    public List<StudentDTO> getByBetweenDates2(LocalDate from, LocalDate to){
        LocalDateTime fromDate = LocalDateTime.of(from, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(to, LocalTime.MAX);
        List<StudentDTO> dtoList = studentRepository.getStudentEntitiesByCreatedDateBetween2(fromDate, toDate);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

    /** 9. Get Pagination */
    public List<StudentDTO> getStudentPagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<StudentDTO> dtoList = new ArrayList<>();
        List<StudentEntity> entityList = studentRepository.findAllBy(pageable);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    public List<StudentDTO> getStudentPagination2(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<StudentDTO> dtoList = studentRepository.findAllBy2(pageable);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

    /** 10. Get Pagination By Level */
    public List<StudentDTO> getStudentPaginationByLevel(int page, int size, int level) {
        Pageable pageable = PageRequest.of(page, size);
        List<StudentDTO> dtoList = new ArrayList<>();
        List<StudentEntity> entityList = studentRepository.findAllByLevelOrderByIdAsc(pageable,level);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;

    }

    public List<StudentDTO> getStudentPaginationByLevel2(int page, int size, int level) {
        Pageable pageable = PageRequest.of(page, size);
        List<StudentDTO> dtoList = studentRepository.findAllByLevelOrderByIdAsc2(level, pageable);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

    /** 11. Get Pagination By Gender */
    public List<StudentDTO> getStudentPaginationByGender(int page, int size, String gender) {
        Pageable pageable = PageRequest.of(page, size);
        List<StudentDTO> dtoList = new ArrayList<>();
        List<StudentEntity> entityList = studentRepository.findAllByGenderOrderById(Gender.valueOf(gender),pageable);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    public List<StudentDTO> getStudentPaginationByGender2(int page, int size, String gender) {
        Pageable pageable = PageRequest.of(page, size);
        List<StudentDTO> dtoList = studentRepository.findAllByGenderOrderById2(Gender.valueOf(gender),pageable);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Student not found");
        }
        return dtoList;
    }

    /** 12. Filter */
    public List<StudentDTO> filter(StudentFilterDTO filterDTO, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        List<StudentDTO> dtoPage = studentCustomRepository.filter(filterDTO, pageable);
        if (dtoPage.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoPage;
    }


    private StudentDTO toDto(StudentEntity studentEntity) {
        StudentDTO dto = new StudentDTO();
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

    private void check(StudentDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()){
            throw  new AppBadRequestException("Where Name?");
        }
        if (dto.getSurname() == null || dto.getSurname().isBlank()){
            throw  new AppBadRequestException("Where Surname?");
        }
    }


}
