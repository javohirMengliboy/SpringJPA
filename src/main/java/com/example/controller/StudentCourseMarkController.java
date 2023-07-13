package com.example.controller;

import com.example.dto.StudentCourseMarkDTO;
import com.example.dto.filter.StudentCourseMarkFilterDTO;
import com.example.dto.filter.StudentFilterDTO;
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

    /** 1. Create */
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody StudentCourseMarkDTO markDTO){
        StudentCourseMarkDTO studentCourseMarkDTO = studentCourseMarkService.create(markDTO);
        return new ResponseEntity<>(studentCourseMarkDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/create/all")
    public ResponseEntity<?> create(@RequestBody List<StudentCourseMarkDTO> list){
        return ResponseEntity.ok(studentCourseMarkService.createAll(list));
    }

    /** 2. Update */
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> put(@RequestBody StudentCourseMarkDTO courseDto, @PathVariable("id") Integer id){
        Boolean response = studentCourseMarkService.update2(id, courseDto);
        if (response){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    /** 3. Get ById */
    @GetMapping(value = "/by_id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentCourseMarkService.getById(id));
    }

    /** 4. Get ById With Detail */
    @GetMapping(value = "/by_id_with_detail/{id}")
    public ResponseEntity<?> getByIdWithDetail(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentCourseMarkService.getByIdWithDetail(id));
    }

    @GetMapping(value = "/by_student_id_with_detail/{id}")
    public ResponseEntity<?> getByStudentIdWithDetail(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentCourseMarkService.findByStudentId(id));
    }

    /** 5. Delete ById */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        Boolean response = studentCourseMarkService.delete2(id);
        if (response){
            return ResponseEntity.ok("Course deleted");
        }
        return ResponseEntity.badRequest().body("Course not found");
    }

    /** 6. Get All */
    @GetMapping(value = "/all")
    public ResponseEntity<?> all(){
        return ResponseEntity.ok(studentCourseMarkService.getAll());
    }

//    @GetMapping(value = "/by_id_with_detail/{id}")
//    public ResponseEntity<?> getByStudentIdWithDetail(@PathVariable("id") Integer id){
//        return ResponseEntity.ok(studentCourseMarkService.getByIdWithDetail2(id));
//    }

    /** 7. Get By Given Date */
    @GetMapping(value = "/get_mark_by_given_date")
    public ResponseEntity<?> getMarkByGivenDate(@RequestParam("studentId") Integer studentId,
                                                @RequestParam("date") LocalDate date){
        return ResponseEntity.ok(studentCourseMarkService.getMarkByGivenDate(studentId, date));
    }

    /** 8. Get By Between Date */
    @GetMapping(value = "/get_mark_by_between_date")
    public ResponseEntity<?> getMarkByBetweenDate(@RequestParam("studentId") Integer studentId,
                                                  @RequestParam("from") LocalDate from,
                                                  @RequestParam("to") LocalDate to){
        return ResponseEntity.ok(studentCourseMarkService.getMarkByBetweenDate(studentId, from, to));
    }

    /** 9. Get All Mark By Student */
    @GetMapping(value = "/get_all_mark_by_student_id")
    public ResponseEntity<?> getAllMarkByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentCourseMarkService.getAllMarkByStudentId(studentId));
    }

    /** 10. Get All Mark Student By Given Course */
    @GetMapping(value = "/get_all_mark_student_by_given_course")
    public ResponseEntity<?> getAllMarkByStudentIdAndByCourseId(@RequestParam("studentId") Integer studentId,
                                                                @RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getAllMarkByStudentIdAndByCourseId(studentId,courseId));
    }

    /** 11. Get Last Mark Student */
    @GetMapping(value = "/get_last_mark_and_course_by_student_id")
    public ResponseEntity<?> getLastMarkAndCourseByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentCourseMarkService.getLastMarkAndCourseByStudentId(studentId));
    }

    /** 12. Get Top 3 Mark Student */
    @GetMapping(value = "/get_top3_mark_and_course_by_student_id")
    public ResponseEntity<?> getTop3MarkAndCourseByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentCourseMarkService.getTop3MarkAndCourseByStudentId(studentId));
    }

    /** 13. Get First Mark Student */
    @GetMapping(value = "/get_first_mark_and_course_by_student_id")
    public ResponseEntity<?> getFirstMarkAndCourseByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentCourseMarkService.getFirstMarkAndCourseByStudentId(studentId));
    }

    /** 14. Get First Mark Student By Given Course */
    @GetMapping(value = "/get_first_mark_by_student_id_and_given_course")
    public ResponseEntity<?> getFirstMarkByStudentIdAndGivenCourse(@RequestParam("studentId") Integer studentId,
                                                                   @RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getFirstMarkByStudentIdAndGivenCourse(studentId, courseId));
    }

    /** 15. Get Top 1 Mark Student By Give Course */
    @GetMapping(value = "/get_top1_mark_by_student_id_and_given_course")
    public ResponseEntity<?> getTop1MarkByStudentIdAndGivenCourse(@RequestParam("studentId") Integer studentId,
                                                                   @RequestParam("courseId") Integer courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getTop1MarkByStudentIdAndGivenCourse(studentId, courseId));
    }

    /** 16. Get Avg Mark Student */
    @GetMapping(value = "/get_avg_mark_by_student_id")
    public ResponseEntity<?> getAvgMarkByStudentId(@RequestParam("studentId") Integer studentId){
        return ResponseEntity.ok(studentCourseMarkService.getAvgMarkByStudentId(studentId));
    }

    /** 17. Get Avg Mark Student By Given Course */
    @GetMapping(value = "/get_avg_mark_by_student_id_and_given_course")
    public ResponseEntity<?> getAvgMarkByStudentIdAndGivenCourse(@RequestParam("studentId") Integer studentId,
                                                                 @RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getAvgMarkByStudentIdAndGivenCourse(studentId, courseId));
    }

    /** 18. Get Student's Count Of Marks Greater Than The Given Mark */
    @GetMapping(value = "/get_count_mark_by_given_mark_older")
    public ResponseEntity<?> getCountMarkByGivenMarkOlder(@RequestParam("studentId") Integer studentId,
                                                          @RequestParam("mark") Double mark){
        return ResponseEntity.ok(studentCourseMarkService.getCountMarkByGivenMarkOlder(studentId,mark));
    }

    /** 19. Get Top 1 Mark By Given Course */
    @GetMapping(value = "/get_top_mark_by_given_course")
    public ResponseEntity<?> getTopMarkByGivenCourse(@RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getTopMarkByGivenCourse(courseId));
    }

    /** 20. Get Avg Mark By Given Course */
    @GetMapping(value = "/get_avg_mark_by_given_course")
    public ResponseEntity<?> getAVgMarkByGivenCourse(@RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getAVgMarkByGivenCourse(courseId));
    }

    /** 21. Get Count Mark By Given Course */
    @GetMapping(value = "/get_count_mark_by_given_course")
    public ResponseEntity<?> getCountMarkByGivenCourse(@RequestParam("courseId") Integer courseId){
        return ResponseEntity.ok(studentCourseMarkService.getCountMarkByGivenCourse(courseId));
    }

    /** 22. Get Pagination */
    @GetMapping(value = "/get_pagination")
    public ResponseEntity<?> getPagination(@RequestParam("page") int page,
                                           @RequestParam("size") int size){
        return ResponseEntity.ok(studentCourseMarkService.getPagination2(page,size));
    }

    /** 23. Get Pagination By StudentId */
    @GetMapping(value = "/get_pagination_by_given_student_id")
    public ResponseEntity<?> getPaginationByStudentId(@RequestParam("studentId") Integer studentId,
                                                      @RequestParam("page") int page,
                                                      @RequestParam("size") int size){
        return ResponseEntity.ok(studentCourseMarkService.getPaginationByStudentId2(studentId,page,size));
    }

    /** 24. Get Pagination By CourseId */
    @GetMapping(value = "/get_pagination_by_given_course_id")
    public ResponseEntity<?> getPaginationByCourseId(@RequestParam("courseId") Integer course_id,
                                                      @RequestParam("page") int page,
                                                      @RequestParam("size") int size){
        return ResponseEntity.ok(studentCourseMarkService.getPaginationByCourseId(course_id,page,size));
    }

    /** 13. Filter */
    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody StudentCourseMarkFilterDTO filterDTO,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(studentCourseMarkService.filter(filterDTO, page, size));
    }







}
