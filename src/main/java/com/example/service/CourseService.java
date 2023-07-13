package com.example.service;

import com.example.dto.CourseDTO;
import com.example.dto.filter.CourseFilterDTO;
import com.example.entity.CourseEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CourseRepository;
import com.example.repository.custom.CourseCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseCustomRepository courseCustomRepository;

    /** 1. Create Course */
    public CourseDTO create(CourseDTO course) {
        check(course);
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(course.getName());
        courseEntity.setPrice(course.getPrice());
        courseEntity.setDuration(course.getDuration());
        courseEntity.setCreatedDate(LocalDateTime.now());
        courseRepository.save(courseEntity);
        course.setId(courseEntity.getId());
        return course;
    }
    public List<CourseDTO> createAll(List<CourseDTO> list) {
        for (CourseDTO dto : list) {
            CourseEntity courseEntity = new CourseEntity();
            courseEntity.setName(dto.getName());
            courseEntity.setPrice(dto.getPrice());
            courseEntity.setDuration(dto.getDuration());
            courseEntity.setCreatedDate(LocalDateTime.now());
            courseRepository.save(courseEntity);
            dto.setId(courseEntity.getId());
        }
        return list;
    }

    /** 2. Get ById */
    public CourseDTO getById(Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        CourseEntity entity = optional.get();
        return toDto(entity);
    }
    public CourseDTO getById2(Integer id) {
        CourseDTO dto = courseRepository.findById2(id);
        if (dto == null){
            throw new ItemNotFoundException("Course not found");
        }
        return dto;
    }

    /** 3. Get All */
    public List<CourseDTO> getAll() {
        Iterable<CourseEntity> iterable = courseRepository.findAll();
        List<CourseDTO> dtoList = new ArrayList<>();
        iterable.forEach(courseEntity -> {
            dtoList.add(toDto(courseEntity));
        });
        return dtoList;
    }

    public List<CourseDTO> getAll2() {
        List<CourseDTO> dtoList = courseRepository.findAll2();
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoList;
    }

    /** 4. Update ById */
    public Boolean update(Integer id, CourseDTO courseDto) {
        check(courseDto);
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()){
            return false;
        }
        CourseEntity entity = optional.get();
        entity.setName(courseDto.getName());
        entity.setPrice(courseDto.getPrice());
        courseRepository.save(entity);
        return true;
    }
    public Boolean update2(Integer id, CourseDTO courseDto) {
        return courseRepository.update2(id,courseDto.getPrice()) > 0;
    }

    /** 5. Delete ById */
    public Boolean delete(Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()){
            return false;
        }
        courseRepository.deleteById(id);
        return true;
    }
    public Boolean delete2(Integer id) {
        return courseRepository.delete2(id) > 0;
    }

    /** 6. Get By Each Field */
    public List<CourseDTO> getByName(String name) {
        List<CourseEntity> entityList = courseRepository.getByName(name);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        List<CourseDTO> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
        return dtoList;
    }
    public List<CourseDTO> getByName2(String name) {
        List<CourseDTO> dtoList = courseRepository.getByName2(name);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoList;
    }

    public List<CourseDTO> getByPrice(Double price) {
        List<CourseEntity> entityList = courseRepository.getByPrice(price);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        List<CourseDTO> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
        return dtoList;
    }
    public List<CourseDTO> getByPrice2(Double price) {
        List<CourseDTO> dtoList = courseRepository.getByPrice2(price);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoList;
    }

    public List<CourseDTO> getByDuration(Integer duration) {
        List<CourseEntity> entityList = courseRepository.getByDuration(duration);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        List<CourseDTO> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
        return dtoList;
    }
    public List<CourseDTO> getByDuration2(Integer duration) {
        List<CourseDTO> dtoList = courseRepository.getByDuration2(duration);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoList;
    }

    /** 7. Get By Between Price */
    public List<CourseDTO> getByPriceBetween(Double from, Double to) {
        List<CourseEntity> entityList = courseRepository.getCourseEntityByPriceBetween(from, to);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        List<CourseDTO> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
        return dtoList;
    }
    public List<CourseDTO> getByPriceBetween2(Double from, Double to) {
        List<CourseDTO> dtoList = courseRepository.getCourseEntityByPriceBetween2(from, to);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoList;
    }

    /** 8. Get By Between Date */
    public List<CourseEntity> getCourseBetweenDates(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.plusDays(1).atStartOfDay().minusNanos(1);
        return courseRepository.getCourseEntityByCreatedDateBetween(startOfDay, endOfDay);
    }

    public List<CourseDTO> getByCreatedDate(LocalDate createdDate) {
        List<CourseDTO> dtoList = courseRepository.getCourseEntityByCreatedDateBetween2(createdDate.atStartOfDay(),createdDate.plusDays(1).atStartOfDay());
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoList;
    }

    /** 9. Pagination */
    public List<CourseDTO> getCoursePagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = new ArrayList<>();
        List<CourseEntity> entityList = courseRepository.findAllBy(pageable);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }
    public List<CourseDTO> getCoursePagination2(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = courseRepository.findAllBy2(pageable);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoList;
    }

    /** 10. Pagination Order By Created Date */
    public List<CourseDTO> getCoursePaginationSortByCreatedDate(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = new ArrayList<>();
        List<CourseEntity> entityList = courseRepository.findAllByOrderByCreatedDateDesc(pageable);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }
    public List<CourseDTO> getCoursePaginationSortByCreatedDate2(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = courseRepository.findAllByOrderByCreatedDateDesc2(pageable);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoList;
    }

    /** 11. Pagination By Price */
    public List<CourseDTO> getCoursePaginationByPrice(int page, int size, double price) {
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = new ArrayList<>();
        List<CourseEntity> entityList = courseRepository.findAllByPriceOrderByCreatedDate(price,pageable);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }
    public List<CourseDTO> getCoursePaginationByPrice2(int page, int size, double price) {
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = courseRepository.findAllByPriceOrderByCreatedDate2(price,pageable);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoList;
    }

    /** 12. Pagination By Between Price */
    public List<CourseDTO> getCoursePaginationByPriceBetween(int page, int size, double from, double to) {
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = new ArrayList<>();
        List<CourseEntity> entityList = courseRepository.findAllByPriceBetweenOrderByCreatedDate(pageable,from, to);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    public List<CourseDTO> getCoursePaginationByPriceBetween2(int page, int size, double from, double to) {
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = courseRepository.findAllByPriceBetweenOrderByCreatedDate2(from, to, pageable);
        if (dtoList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoList;
    }

    /** 13. Filter */
    public List<CourseDTO> filter(CourseFilterDTO filterDTO, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        List<CourseDTO> dtoPage = courseCustomRepository.filter(filterDTO, pageable);
        if (dtoPage.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        return dtoPage;
    }

    private CourseDTO toDto(CourseEntity courseEntity) {
        CourseDTO courseDto = new CourseDTO();
        courseDto.setId(courseEntity.getId());
        courseDto.setName(courseEntity.getName());
        courseDto.setPrice(courseEntity.getPrice());
        courseDto.setDuration(courseEntity.getDuration());
        courseDto.setCreatedDate(courseEntity.getCreatedDate());
        return courseDto;
    }

    private void check(CourseDTO course) {
        if (course.getName() == null || course.getName().isBlank()){
            throw  new AppBadRequestException("Where Name?");
        }
    }
}
