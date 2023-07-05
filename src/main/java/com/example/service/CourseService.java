package com.example.service;

import com.example.dto.CourseDto;
import com.example.dto.StudentDto;
import com.example.entity.CourseEntity;
import com.example.entity.StudentEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CourseDto add(CourseDto course) {
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

    private void check(CourseDto course) {
        if (course.getName() == null || course.getName().isBlank()){
            throw  new AppBadRequestException("Where Name?");
        }
    }

    public List<CourseDto> addAll(List<CourseDto> list) {
        for (CourseDto dto : list) {
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

    public List<CourseDto> getAll() {
        Iterable<CourseEntity> iterable = courseRepository.findAll();
        List<CourseDto> dtoList = new ArrayList<>();
        iterable.forEach(courseEntity -> {
            dtoList.add(toDto(courseEntity));
        });
        return dtoList;
    }

    private CourseDto toDto(CourseEntity courseEntity) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(courseEntity.getId());
        courseDto.setName(courseEntity.getName());
        courseDto.setPrice(courseEntity.getPrice());
        courseDto.setDuration(courseEntity.getDuration());
        courseDto.setCreatedDate(courseEntity.getCreatedDate());
        return courseDto;
    }

    public CourseDto getById(Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        CourseEntity entity = optional.get();
        return toDto(entity);
    }

    public List<CourseDto> getByName(String name) {
        List<CourseEntity> entityList = courseRepository.getByName(name);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        List<CourseDto> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
        return dtoList;
    }

    public List<CourseDto> getByPrice(Double price) {
        List<CourseEntity> entityList = courseRepository.getByPrice(price);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        List<CourseDto> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
        return dtoList;
    }

    public List<CourseDto> getByDuration(Integer duration) {
        List<CourseEntity> entityList = courseRepository.getByDuration(duration);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        List<CourseDto> dtoList = new ArrayList<>();
        entityList.forEach((e) -> dtoList.add(toDto(e)));
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
    public Boolean update(Integer id, CourseDto courseDto) {
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

    public List<CourseDto> getByCreatedDate(LocalDate createdDate) {
        List<CourseEntity> courseEntities = courseRepository.getCourseEntityByCreatedDateBetween(createdDate.atStartOfDay(),createdDate.plusDays(1).atStartOfDay());
        if (courseEntities.isEmpty()){
            throw new ItemNotFoundException("Course not found");
        }
        List<CourseDto> dtoList = new ArrayList<>();
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
