package com.example.repository.custom;

import com.example.dto.CourseDTO;
import com.example.dto.StudentDTO;
import com.example.dto.filter.StudentFilterDTO;
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
public class StudentCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public List<StudentDTO> filter(StudentFilterDTO filterDTO, Pageable pageable) {
        StringBuilder builder = new StringBuilder();
        builder.append("select new com.example.dto.StudentDTO(id, name, surname,level, age, gender, phone,createdDate) from StudentEntity where 1 = 1 ");
        Map<String, Object> map = new HashMap<>();
        if (filterDTO.getName() != null){
            builder.append("and name = :name ");
            map.put("name", filterDTO.getName());
        }
        if (filterDTO.getSurname() != null){
            builder.append("and surname = :surname ");
            map.put("surname", filterDTO.getSurname());
        }
        if (filterDTO.getLevel() != null){
            builder.append("and level = :level ");
            map.put("level", filterDTO.getLevel());
        }
        if (filterDTO.getAge() != null){
            builder.append("and age = :age ");
            map.put("age", filterDTO.getAge());
        }
        if (filterDTO.getGender() != null){
            builder.append("and gender = :gender ");
            map.put("gender", filterDTO.getGender());
        }
        if (filterDTO.getCreatedDate() != null){
            LocalDateTime from = LocalDateTime.of(filterDTO.getCreatedDate(), LocalTime.MIN);
            LocalDateTime to = LocalDateTime.of(filterDTO.getCreatedDate(), LocalTime.MAX);
            builder.append("and createdDate > :from and createdDate < :to ");
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
