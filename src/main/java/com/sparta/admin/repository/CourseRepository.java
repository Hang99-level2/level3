package com.sparta.admin.repository;

import com.sparta.admin.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByCategoryOrderByDateAddedDesc(String category);
}
