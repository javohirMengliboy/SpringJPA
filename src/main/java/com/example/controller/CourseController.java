package com.example.controller;

import com.example.dto.CourseDTO;
import com.example.entity.CourseEntity;
import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    /** 1. Create Course */
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody CourseDTO course){
        CourseDTO studentDto = courseService.create(course);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping(value = "/create/all")
    public ResponseEntity<?> create(@RequestBody List<CourseDTO> list){
        return ResponseEntity.ok(courseService.createAll(list));
    }

    /** 2. Get ById */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(courseService.getById(id));
    }

    /** 3. Get All */
    @GetMapping(value = "/all")
    public ResponseEntity<?> all(){
        return ResponseEntity.ok(courseService.getAll());
    }

    /** 4. Update ById */
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> put(@RequestBody CourseDTO courseDto, @PathVariable("id") Integer id){
        Boolean response = courseService.update(id, courseDto);
        if (response){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    /** 5. Delete ById */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        Boolean response = courseService.delete(id);
        if (response){
            return ResponseEntity.ok("Course deleted");
        }
        return ResponseEntity.badRequest().body("Course not found");
    }

    /** 6. Get By Each Field */
    @GetMapping(value = "/name")
    public ResponseEntity<?> getByName(@RequestParam("name") String name){
        return ResponseEntity.ok(courseService.getByName(name));

    }

    @GetMapping(value = "/price")
    public ResponseEntity<?> getByPrice(@RequestParam("price") Double price){
        return ResponseEntity.ok(courseService.getByPrice(price));
    }

    @GetMapping(value = "/duration")
    public ResponseEntity<?> getByDuration(@RequestParam("duration") Integer duration){
        return ResponseEntity.ok(courseService.getByDuration(duration));
    }

    /** 7. Get By Between Price */
    @GetMapping(value = "/get_price_between")
    public ResponseEntity<?> getByPriceBetween(@RequestParam("from") Double from,
                                               @RequestParam("to") Double to){
        return ResponseEntity.ok(courseService.getByPriceBetween(from, to));
    }

    /** 8. Get By Between Date */
    @GetMapping(value = "/created_date")
    public ResponseEntity<?> getByCreatedDate(@RequestParam("created_date") LocalDate createdDate){
        return ResponseEntity.ok(courseService.getByCreatedDate(createdDate));
    }

    @GetMapping("/get_between_date")
    public ResponseEntity<List<CourseEntity>> getStudentsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<CourseEntity> studentList = courseService.getCourseBetweenDates(startDate, endDate);
        return ResponseEntity.ok(studentList);
    }

    /** 9. Pagination */
    @GetMapping("/pagination")
    public ResponseEntity<List<CourseDTO>> getStudentPagination(@RequestParam int page,
                                                                @RequestParam int size) {
        List<CourseDTO> studentList = courseService.getCoursePagination(page, size);
        return ResponseEntity.ok(studentList);
    }

    /** 10. Pagination Order By Created Date */
    @GetMapping("/pagination/sort_by_created_date")
    public ResponseEntity<List<CourseDTO>> getCoursePaginationSortByCreatedDate(@RequestParam int page,
                                                                                @RequestParam int size) {
        List<CourseDTO> studentList = courseService.getCoursePaginationSortByCreatedDate(page, size);
        return ResponseEntity.ok(studentList);
    }

    /** 11. Pagination By Price */
    @GetMapping("/pagination/by_price")
    public ResponseEntity<List<CourseDTO>> getCoursePaginationByPrice(@RequestParam int page,
                                                                      @RequestParam int size,
                                                                      @RequestParam double price) {
        List<CourseDTO> studentList = courseService.getCoursePaginationByPrice(page, size, price);
        return ResponseEntity.ok(studentList);
    }

    /** 12. Pagination By Between Price */
    @GetMapping("/pagination/by_price_between")
    public ResponseEntity<List<CourseDTO>> getCoursePaginationByPriceBetween(@RequestParam int page,
                                                                             @RequestParam int size,
                                                                             @RequestParam double from,
                                                                             @RequestParam double to) {
        List<CourseDTO> studentList = courseService.getCoursePaginationByPriceBetween(page, size, from,to);
        return ResponseEntity.ok(studentList);
    }
}
