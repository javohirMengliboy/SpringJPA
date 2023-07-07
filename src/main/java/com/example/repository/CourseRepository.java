package com.example.repository;

import com.example.entity.CourseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer> {
    List<CourseEntity> getByName(String name);
    List<CourseEntity> getByPrice(Double price);
    List<CourseEntity> getByDuration(Integer duration);
    List<CourseEntity> getCourseEntityByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<CourseEntity> findAllBy(Pageable pageable);
    List<CourseEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);
    List<CourseEntity> findAllByPriceOrderByCreatedDate(double price, Pageable pageable);

    List<CourseEntity> findAllByPriceBetweenOrderByCreatedDate(Pageable pageable, double from, double to);
}
