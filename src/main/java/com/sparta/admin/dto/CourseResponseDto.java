package com.sparta.admin.dto;

import com.sparta.admin.entity.Course;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponseDto {

    private String courseName;
    private int price;
    private String description;
    private String category;
    private long teacherId;
    private String dateAdded;

    public CourseResponseDto(Course course) {
        this.courseName = course.getCourseName();
        this.price = course.getPrice();
        this.description = course.getDescription();
        this.category = course.getCategory();
        this.teacherId = course.getTeacher().getId();
        this.dateAdded = course.getDateAdded();
    }
}
