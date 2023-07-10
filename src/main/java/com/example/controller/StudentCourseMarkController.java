package com.example.controller;

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
        return ResponseEntity.ok(studentCourseMarkService.getAll2());
    }

    @GetMapping(value = "/by_id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentCourseMarkService.getById(id));
    }

    @GetMapping(value = "/by_id_with_detail/{id}")
    public ResponseEntity<?> getByIdWithDetail(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentCourseMarkService.getByIdWithDetail(id));
    }

    @GetMapping(value = "/by_id_with_detail/{id}")
    public ResponseEntity<?> getByStudentIdWithDetail(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentCourseMarkService.getByStudentIdWithDetail(id));
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
        return ResponseEntity.ok(studentCourseMarkService.getAllMarkByStudentId2(studentId));
    }

    @GetMapping(value = "/get_all_mark_by_student_id_and_by_course_id")
    public ResponseEntity<?> getAllMarkByStudentIdAndByCourseId(@RequestParam("studentId") Integer studentId,
                                                                @RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getAllMarkByStudentIdAndByCourseId2(studentId,courseId));
    }

    @GetMapping(value = "/get_last_mark_and_course_by_student_id")
    public ResponseEntity<?> getLastMarkAndCourseByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentCourseMarkService.getLastMarkAndCourseByStudentId(studentId));
    }


    @GetMapping(value = "/get_first_mark_and_course_by_student_id")
    public ResponseEntity<?> getFirstMarkAndCourseByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentCourseMarkService.getFirstMarkAndCourseByStudentId(studentId));
    }

    @GetMapping(value = "/get_top3_mark_and_course_by_student_id")
    public ResponseEntity<?> getTop3MarkAndCourseByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentCourseMarkService.getTop3MarkAndCourseByStudentId(studentId));
    }

    @GetMapping(value = "/get_first_mark_by_student_id_and_given_course")
    public ResponseEntity<?> getFirstMarkByStudentIdAndGivenCourse(@RequestParam("studentId") Integer studentId,
                                                                   @RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getFirstMarkByStudentIdAndGivenCourse2(studentId, courseId));
    }
    @GetMapping(value = "/get_top1_mark_by_student_id_and_given_course")
    public ResponseEntity<?> getTop1MarkByStudentIdAndGivenCourse(@RequestParam("studentId") Integer studentId,
                                                                   @RequestParam("courseId") Integer courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getTop1MarkByStudentIdAndGivenCourse(studentId, courseId));
    }

    @GetMapping(value = "/get_avg_mark_by_student_id")
    public ResponseEntity<?> getAvgMarkByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentCourseMarkService.getAvgMarkByStudentId(studentId));
    }
    @GetMapping(value = "/get_count_mark_by_given_mark_older")
    public ResponseEntity<?> getCountMarkByGivenMarkOlder(@RequestParam("studentId") Integer studentId,
                                                          @RequestParam("mark") Double mark){
        return ResponseEntity.ok(studentCourseMarkService.getCountMarkByGivenMarkOlder(studentId,mark));
    }


    @GetMapping(value = "/get_avg_mark_by_student_id_and_given_course")
    public ResponseEntity<?> getAvgMarkByStudentIdAndGivenCourse(@RequestParam("studentId") Integer studentId,
                                                                  @RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getAvgMarkByStudentIdAndGivenCourse(studentId, courseId));
    }

    @GetMapping(value = "/get_top_mark_by_given_course")
    public ResponseEntity<?> getTopMarkByGivenCourse(@RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getTopMarkByGivenCourse(courseId));
    }

    @GetMapping(value = "/get_avg_mark_by_given_course")
    public ResponseEntity<?> getAVgMarkByGivenCourse(@RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getAVgMarkByGivenCourse(courseId));
    }

    @GetMapping(value = "/get_count_mark_by_given_course")
    public ResponseEntity<?> getCountMarkByGivenCourse(@RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getCountMarkByGivenCourse(courseId));
    }
    @GetMapping(value = "/get_pagination")
    public ResponseEntity<?> getPagination(@RequestParam("page") int page,
                                           @RequestParam("size") int size){
        return ResponseEntity.ok(studentCourseMarkService.getPagination(page,size));
    }

    @GetMapping(value = "/get_pagination_by_given_student_id")
    public ResponseEntity<?> getPaginationByStudentId(@RequestParam("studentId") Integer studentId,
                                                      @RequestParam("page") int page,
                                                      @RequestParam("size") int size){
        return ResponseEntity.ok(studentCourseMarkService.getPaginationByStudentId(studentId,page,size));
    }
    @GetMapping(value = "/get_pagination_by_given_course_id")
    public ResponseEntity<?> getPaginationByCourseId(@RequestParam("courseId") Integer course_id,
                                                      @RequestParam("page") int page,
                                                      @RequestParam("size") int size){
        return ResponseEntity.ok(studentCourseMarkService.getPaginationByCourseId(course_id,page,size));
    }




    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        Boolean response = studentCourseMarkService.delete2(id);
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

    @PutMapping(value = "/update")
    public ResponseEntity<?> put(@RequestParam("mark") Double mark, @RequestParam("id") Integer id){
        Boolean response = studentCourseMarkService.update2(mark, id);
        if (response){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }
}
