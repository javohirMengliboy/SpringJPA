package com.example.controller;

import com.example.dto.CourseDTO;
import com.example.dto.StudentCourseMarkDTO;
import com.example.service.StudentCourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/student_course_mark")
public class StudentCourseMarkController {
    @Autowired
    private StudentCourseMarkService studentCourseMarkService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody StudentCourseMarkDTO markDTO){
        StudentCourseMarkDTO studentCourseMarkDTO = studentCourseMarkService.add(markDTO);
        return new ResponseEntity<>(studentCourseMarkDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/create/all")
    public ResponseEntity<?> create(@RequestBody List<StudentCourseMarkDTO> list){
        return ResponseEntity.ok(studentCourseMarkService.addAll(list));
    }
    @GetMapping(value = "/all")
    public ResponseEntity<?> all(){
        return ResponseEntity.ok(studentCourseMarkService.getAll());
    }

    @GetMapping(value = "/by_id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentCourseMarkService.getById(id));
    }

    @GetMapping(value = "/by_id_with_detail/{id}")
    public ResponseEntity<?> getByIdWithDetail(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentCourseMarkService.getByIdWithDetail(id));
    }

    @GetMapping(value = "/get_mark_by_given_date")
    public ResponseEntity<?> getMarkByGivenDate(@RequestParam("studentId") Integer studentId,
                                                @RequestParam("date") LocalDate date){
        return ResponseEntity.ok(studentCourseMarkService.getMarkByGivenDate(studentId, date));
    }

    @GetMapping(value = "/get_mark_by_between_date")
    public ResponseEntity<?> getMarkByBetweenDate(@RequestParam("studentId") Integer studentId,
                                                  @RequestParam("from") LocalDate from,
                                                  @RequestParam("to") LocalDate to){
        return ResponseEntity.ok(studentCourseMarkService.getMarkByBetweenDate(studentId, from, to));
    }

    @GetMapping(value = "/get_all_mark_by_student_id")
    public ResponseEntity<?> getAllMarkByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentCourseMarkService.getAllMarkByStudentId(studentId));
    }

    @GetMapping(value = "/get_all_mark_by_student_id_and_by_course_id")
    public ResponseEntity<?> getAllMarkByStudentIdAndByCourseId(@RequestParam("studentId") Integer studentId,
                                                                @RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getAllMarkByStudentIdAndByCourseId(studentId,courseId));
    }

    @GetMapping(value = "/get_mark_and_course_by_student_id")
    public ResponseEntity<?> getMarkAndCourseByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentCourseMarkService.getMarkAndCourseByStudentId(studentId));
    }





    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        Boolean response = studentCourseMarkService.delete(id);
        if (response){
            return ResponseEntity.ok("Course deleted");
        }
        return ResponseEntity.badRequest().body("Course not found");
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> put(@RequestBody StudentCourseMarkDTO courseDto, @PathVariable("id") Integer id){
        Boolean response = studentCourseMarkService.update(id, courseDto);
        if (response){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }
}
