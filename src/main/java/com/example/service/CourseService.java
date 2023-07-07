package com.example.service;

import com.example.dto.CourseDTO;
import com.example.entity.CourseEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CourseRepository;
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
    public CourseDTO add(CourseDTO course) {
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

    private void check(CourseDTO course) {
        if (course.getName() == null || course.getName().isBlank()){
            throw  new AppBadRequestException("Where Name?");
        }
    }

    public List<CourseDTO> addAll(List<CourseDTO> list) {
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

    public List<CourseDTO> getAll() {
        Iterable<CourseEntity> iterable = courseRepository.findAll();
        List<CourseDTO> dtoList = new ArrayList<>();
        iterable.forEach(courseEntity -> {
            dtoList.add(toDto(courseEntity));
        });
        return dtoList;
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

    public CourseDTO getById(Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        CourseEntity entity = optional.get();
        return toDto(entity);
    }

    public List<CourseDTO> getByName(String name) {
        List<CourseEntity> entityList = courseRepository.getByName(name);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        List<CourseDTO> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
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

    public List<CourseDTO> getByDuration(Integer duration) {
        List<CourseEntity> entityList = courseRepository.getByDuration(duration);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        List<CourseDTO> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
        return dtoList;
    }

    public List<CourseDTO> getCoursePagination(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = new ArrayList<>();
        List<CourseEntity> entityList = courseRepository.findAllBy(pageable);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    public List<CourseDTO> getCoursePaginationSortByCreatedDate(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = new ArrayList<>();
        List<CourseEntity> entityList = courseRepository.findAllByOrderByCreatedDateDesc(pageable);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;

    }

    public List<CourseDTO> getCoursePaginationByPrice(int page, int size, double price) {
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = new ArrayList<>();
        List<CourseEntity> entityList = courseRepository.findAllByPriceOrderByCreatedDate(price,pageable);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    public List<CourseDTO> getCoursePaginationByPriceBetween(int page, int size, double from, double to) {
        Pageable pageable = PageRequest.of(page, size);
        List<CourseDTO> dtoList = new ArrayList<>();
        List<CourseEntity> entityList = courseRepository.findAllByPriceBetweenOrderByCreatedDate(pageable,from, to);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }


    public Boolean delete(Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()){
            return false;
        }
        courseRepository.deleteById(id);
        return true;
    }
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

    public List<CourseDTO> getByCreatedDate(LocalDate createdDate) {
        List<CourseEntity> courseEntities = courseRepository.getCourseEntityByCreatedDateBetween(createdDate.atStartOfDay(),createdDate.plusDays(1).atStartOfDay());
        if (courseEntities.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        List<CourseDTO> dtoList = new ArrayList<>();
        for (CourseEntity entity : courseEntities) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    public List<CourseEntity> getStudentsBetweenDates(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.plusDays(1).atStartOfDay().minusNanos(1);
        return courseRepository.getCourseEntityByCreatedDateBetween(startOfDay, endOfDay);
    }


}
