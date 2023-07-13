package com.example.repository;

import com.example.dto.CourseDTO;
import com.example.dto.StudentDTO;
import com.example.entity.CourseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer> {
    /** 2. Get ById */
    @Query("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate) from CourseEntity " +
            "where id = :id")
    CourseDTO findById2(@Param("id") Integer id);

    /** 3. Get All */
    @Query("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate) from CourseEntity ")
    List<CourseDTO> findAll2();

    /** 4. Update ById */
    @Transactional
    @Modifying
    @Query("update CourseEntity set price = :price where id = :id")
    int update2(@Param("id") Integer id, @Param("price") Double price);

    /** 5. Delete ById */
    @Transactional
    @Modifying
    @Query("delete from CourseEntity where id = :id")
    int delete2(@Param("id") Integer id);

    /** 6. Get By Each Field */
    List<CourseEntity> getByName(String name);
    @Query("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate) from CourseEntity " +
            "where name = :name")
    List<CourseDTO> getByName2(@Param("name") String name);

    List<CourseEntity> getByPrice(Double price);

    @Query("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate) from CourseEntity " +
            "where price = :price")
    List<CourseDTO> getByPrice2(@Param("price") Double price);

    List<CourseEntity> getByDuration(Integer duration);

    @Query("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate) from CourseEntity " +
            "where duration = :duration")
    List<CourseDTO> getByDuration2(@Param("duration") Integer duration);

    /** 7. Get By Between Price */
    List<CourseEntity> getCourseEntityByPriceBetween(Double from, Double to);

    @Query("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate) from CourseEntity " +
            "where price >= :from and price < :to")
    List<CourseDTO> getCourseEntityByPriceBetween2(@Param("from") Double from,
                                                   @Param("to") Double to);

    /** 8. Get By Between Date */
    List<CourseEntity> getCourseEntityByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate) from CourseEntity " +
            "where createdDate > :from and createdDate < :to")
    List<CourseDTO> getCourseEntityByCreatedDateBetween2(@Param("from") LocalDateTime startDate,
                                                         @Param("to") LocalDateTime endDate);

    /** 9. Pagination */
    List<CourseEntity> findAllBy(Pageable pageable);

    @Query("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate) from CourseEntity")
    List<CourseDTO> findAllBy2(Pageable pageable);

    /** 10. Pagination Order By Created Date */
    List<CourseEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate) from CourseEntity" +
            " order by createdDate desc")
    List<CourseDTO> findAllByOrderByCreatedDateDesc2(Pageable pageable);

    /** 11. Pagination By Price */
    List<CourseEntity> findAllByPriceOrderByCreatedDate(Double price, Pageable pageable);

    @Query("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate) from CourseEntity" +
            " where price = :price order by createdDate desc")
    List<CourseDTO> findAllByPriceOrderByCreatedDate2(@Param("price") Double price, Pageable pageable);

    /** 12. Pagination By Between Price */
    List<CourseEntity> findAllByPriceBetweenOrderByCreatedDate(Pageable pageable, double from, double to);
    @Query("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate) from CourseEntity" +
            " where price >= :from and price < :to order by createdDate desc")
    List<CourseDTO> findAllByPriceBetweenOrderByCreatedDate2(@Param("from") Double from,
                                                             @Param("to") Double to,
                                                             Pageable pageable);

}