package com.sparta.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequestDto {
    private String courseName;
    private int price;
    private String description;
    private String category;
    private long teacherId;
    private String dateAdded;
}
