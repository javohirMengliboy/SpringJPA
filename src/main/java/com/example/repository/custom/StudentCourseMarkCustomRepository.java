package com.example.repository.custom;

import com.example.dto.CourseDTO;
import com.example.dto.StudentCourseMarkDTO;
import com.example.dto.filter.CourseFilterDTO;
import com.example.dto.filter.StudentCourseMarkFilterDTO;
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
public class StudentCourseMarkCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public List<StudentCourseMarkDTO> filter(StudentCourseMarkFilterDTO filterDTO, Pageable pageable) {
        StringBuilder builder = new StringBuilder();
        builder.append("select new com.example.dto.StudentCourseMarkDTO(id, student.id, course.id, mark, createdDate)" +
                " from  StudentCourseMarkEntity where 1 = 1 ");
        Map<String, Object> map = new HashMap<>();
        if (filterDTO.getStudent() != null){
            builder.append("and student.id = :student ");
            map.put("student", filterDTO.getStudent());
        }
        if (filterDTO.getCourse() != null){
            builder.append("and course.id = :course ");
            map.put("course", filterDTO.getCourse());
        }
        if (filterDTO.getMark() != null){
            builder.append("and mark = :mark ");
            map.put("mark", filterDTO.getMark());
        }
        if (filterDTO.getCreatedDate() != null){
            LocalDateTime from = LocalDateTime.of(filterDTO.getCreatedDate(), LocalTime.MIN);
            LocalDateTime to = LocalDateTime.of(filterDTO.getCreatedDate(), LocalTime.MAX);
            builder.append("and createdDate > :from and createdDate < to ");
            map.put("from", from);
            map.put("to", to);
        }

        builder.append(" order by createdDate desc");
        System.out.println(builder.toString());
        Query query = entityManager.createQuery(builder.toString());

        for (Map.Entry<String, Object> param : map.entrySet()) {
            query.setParameter(param.getKey(),param.getValue());
        }

        return query.getResultList();
    }
}
