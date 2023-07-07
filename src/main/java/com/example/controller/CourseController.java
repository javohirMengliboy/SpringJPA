package com.example.controller;

import com.example.dto.CourseDTO;
import com.example.entity.CourseEntity;
import com.example.exp.ItemNotFoundException;
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

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody CourseDTO course){
        CourseDTO studentDto = courseService.add(course);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping(value = "/create/all")
    public ResponseEntity<?> create(@RequestBody List<CourseDTO> list){
        return ResponseEntity.ok(courseService.addAll(list));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> all(){
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(courseService.getById(id));
    }

    @GetMapping(value = "/name")
    public ResponseEntity<?> getByName(@RequestParam("name") String name){
        try {
            return ResponseEntity.ok(courseService.getByName(name));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/price")
    public ResponseEntity<?> getByPrice(@RequestParam("price") Double price){
        try {
            return ResponseEntity.ok(courseService.getByPrice(price));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/duration")
    public ResponseEntity<?> getByDuration(@RequestParam("duration") Integer duration){
        try {
            return ResponseEntity.ok(courseService.getByDuration(duration));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/created_date")
    public ResponseEntity<?> getByCreatedDate(@RequestParam("created_date") LocalDate createdDate){
        try {
            return ResponseEntity.ok(courseService.getByCreatedDate(createdDate));
        }catch (ItemNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<CourseEntity>> getStudentsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<CourseEntity> studentList = courseService.getStudentsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<CourseDTO>> getStudentPagination(@RequestParam int page,
                                                                @RequestParam int size) {
        List<CourseDTO> studentList = courseService.getCoursePagination(page, size);
        return ResponseEntity.ok(studentList);
    }
    @GetMapping("/pagination/sortByCreated_date")
    public ResponseEntity<List<CourseDTO>> getCoursePaginationSortByCreatedDate(@RequestParam int page,
                                                                                @RequestParam int size) {
        List<CourseDTO> studentList = courseService.getCoursePaginationSortByCreatedDate(page, size);
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("/pagination/by_price")
    public ResponseEntity<List<CourseDTO>> getCoursePaginationByPrice(@RequestParam int page,
                                                                      @RequestParam int size,
                                                                      @RequestParam double price) {
        List<CourseDTO> studentList = courseService.getCoursePaginationByPrice(page, size, price);
        return ResponseEntity.ok(studentList);
    }

    @GetMapping("/pagination/by_price_between")
    public ResponseEntity<List<CourseDTO>> getCoursePaginationByPriceBetween(@RequestParam int page,
                                                                             @RequestParam int size,
                                                                             @RequestParam double from,
                                                                             @RequestParam double to) {
        List<CourseDTO> studentList = courseService.getCoursePaginationByPriceBetween(page, size, from,to);
        return ResponseEntity.ok(studentList);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        Boolean response = courseService.delete(id);
        if (response){
            return ResponseEntity.ok("Course deleted");
        }
        return ResponseEntity.badRequest().body("Course not found");
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> put(@RequestBody CourseDTO courseDto, @PathVariable("id") Integer id){
        Boolean response = courseService.update(id, courseDto);
        if (response){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }
}
