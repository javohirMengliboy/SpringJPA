package com.example.controller;

import com.example.dto.StudentDTO;
import com.example.entity.StudentEntity;
import com.example.enums.Gender;
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

    /** 1. Create Student */
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody StudentDTO student){
        StudentDTO studentDto = studentService.create(student);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping(value = "/create/all")
    public ResponseEntity<?> create(@RequestBody List<StudentDTO> list){
        return ResponseEntity.ok(studentService.createAll(list));
    }

    /** 2. Get All Student */
    @GetMapping(value = "/all")
    public ResponseEntity<?> all(){
        return ResponseEntity.ok(studentService.getAll2());
    }

    /** 3. Get ById */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentService.getById2(id));
    }

    /** 4. Update ById */
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> put(@RequestBody StudentDTO student, @PathVariable("id") Integer id){
        Boolean response = studentService.update(id, student);
        if (response){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    /** 5 Delete ById */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        Boolean response = studentService.delete(id);
        if (response){
            return ResponseEntity.ok("Student deleted");
        }
        return ResponseEntity.badRequest().body("Student not found");
    }

    /** 6. Get By Each Field */
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

    @GetMapping(value = "/age")
    public ResponseEntity<?> getByGender(@RequestParam("gender") String gender){
        try {
            return ResponseEntity.ok(studentService.getByGender(Gender.valueOf(gender)));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/gender")
    public ResponseEntity<?> getByAge(@RequestParam("age") Integer age){
        try {
            return ResponseEntity.ok(studentService.getByAge(age));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** 7. Get By Given Date */
    @GetMapping(value = "/given_date")
    public ResponseEntity<?> getByCreatedDate(@RequestParam("given_date") LocalDate createdDate){
        try {
            return ResponseEntity.ok(studentService.getByGivenDate(createdDate));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** 8. Get By Between Date */
    @GetMapping("/between_date")
    public ResponseEntity<List<StudentEntity>> getStudentsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<StudentEntity> studentList = studentService.getByBetweenDates(startDate, endDate);
        return ResponseEntity.ok(studentList);
    }

    /** 9. Get Pagination */
    @GetMapping("/pagination")
    public ResponseEntity<List<StudentDTO>> getStudentPagination(@RequestParam int page,
                                                                 @RequestParam int size) {
        List<StudentDTO> studentList = studentService.getStudentPagination(page, size);
        return ResponseEntity.ok(studentList);
    }

    /** 10. Get Pagination By Level */
    @GetMapping("/pagination/by_level")
    public ResponseEntity<List<StudentDTO>> getStudentPaginationByLevel(@RequestParam int page,
                                                                        @RequestParam int size,
                                                                        @RequestParam int level) {
        List<StudentDTO> studentList = studentService.getStudentPaginationByLevel(page, size, level);
        return ResponseEntity.ok(studentList);
    }

    /** 11. Get Pagination By Gender */
    @GetMapping("/pagination/by_gender")
    public ResponseEntity<List<StudentDTO>> getStudentPaginationByGender(@RequestParam int page,
                                                                         @RequestParam int size,
                                                                         @RequestParam String gender) {
        List<StudentDTO> studentList = studentService.getStudentPaginationByGender(page, size, gender);
        return ResponseEntity.ok(studentList);
    }




}
