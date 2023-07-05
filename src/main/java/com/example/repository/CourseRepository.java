package com.example.repository;

import com.example.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer> {
    List<CourseEntity> getByName(String name);
    List<CourseEntity> getByPrice(Double price);
    List<CourseEntity> getByDuration(Integer duration);
    List<CourseEntity> getCourseEntityByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
