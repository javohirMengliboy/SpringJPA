package com.example.controller;

import com.example.dto.StudentDto;
import com.example.entity.StudentEntity;
import com.example.exp.ItemNotFoundException;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody StudentDto student){
        StudentDto studentDto = studentService.add(student);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping(value = "/create/all")
    public ResponseEntity<?> create(@RequestBody List<StudentDto> list){
        return ResponseEntity.ok(studentService.addAll(list));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> all(){
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping(value = "/phone")
    public ResponseEntity<?> getByPhone(@RequestParam("phone") String phone){
        return ResponseEntity.ok(studentService.getByPhone(phone));
    }

    @GetMapping(value = "/name")
    public ResponseEntity<?> getByName(@RequestParam("name") String name){
        try {
            return ResponseEntity.ok(studentService.getByName(name));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/surname")
    public ResponseEntity<?> getBySurname(@RequestParam("surname") String surname){
        try {
            return ResponseEntity.ok(studentService.getBySurname(surname));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/level")
    public ResponseEntity<?> getByLevel(@RequestParam("level") Integer level){
        try {
            return ResponseEntity.ok(studentService.getByLevel(level));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/gender")
    public ResponseEntity<?> getByGender(@RequestParam("gender") String gender){
        try {
            return ResponseEntity.ok(studentService.getByGender(gender));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping(value = "/created_date")
    public ResponseEntity<?> getByCreatedDate(@RequestParam("created_date") LocalDate createdDate){
        try {
            return ResponseEntity.ok(studentService.getByCreatedDate(createdDate));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<StudentEntity>> getStudentsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<StudentEntity> studentList = studentService.getStudentsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(studentList);
    }

    @DeleteMapping(value = "/delete/all")
    public ResponseEntity<?> deleteAll(){
        studentService.deleteAll();
        return ResponseEntity.ok("All deleted");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        Boolean response = studentService.delete(id);
        if (response){
            return ResponseEntity.ok("Student deleted");
        }
        return ResponseEntity.badRequest().body("Student not found");
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> put(@RequestBody StudentDto student,@PathVariable("id") Integer id){
        Boolean response = studentService.update(id, student);
        if (response){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }
}
