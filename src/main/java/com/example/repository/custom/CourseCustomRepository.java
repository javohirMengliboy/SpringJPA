package com.example.repository.custom;

import com.example.dto.CourseDTO;
import com.example.dto.filter.CourseFilterDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CourseCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public List<CourseDTO> filter(CourseFilterDTO filterDTO, Pageable pageable) {
        StringBuilder builder = new StringBuilder();
        builder.append("select new com.example.dto.CourseDTO(id, name, price, duration, createdDate)" +
                " from  CourseEntity where 1 = 1 ");
        Map<String, Object> map = new HashMap<>();
        if (filterDTO.getName() != null){
            builder.append("and name = :name ");
            map.put("name", filterDTO.getName());
        }
        if (filterDTO.getPrice() != null){
            builder.append("and price = :price ");
            map.put("price", filterDTO.getPrice());
        }
        if (filterDTO.getDuration() != null){
            builder.append("and duration = :duration ");
            map.put("duration", filterDTO.getDuration());
        }
        if (filterDTO.getCreatedDate() != null){
            LocalDateTime from = LocalDateTime.of(filterDTO.getCreatedDate(), LocalTime.MIN);
            LocalDateTime to = LocalDateTime.of(filterDTO.getCreatedDate(), LocalTime.MAX);
            builder.append("and createdDate > :from and createdDate < to ");
            map.put("from", from);
            map.put("to", to);
        }

        builder.append(" order by createdDate");
        Query query = entityManager.createQuery(builder.toString());

        for (Map.Entry<String, Object> param : map.entrySet()) {
            query.setParameter(param.getKey(),param.getValue());
        }

        return query.getResultList();
    }
}
