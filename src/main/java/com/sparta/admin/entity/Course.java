package com.sparta.admin.entity;


import com.sparta.admin.dto.CourseRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="course_name",nullable = false)
    private String courseName;

    @Column(name ="price", nullable = false)
    private int price;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name ="category",nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name="date_added", nullable = false)
    private String dateAdded;

    public Course(CourseRequestDto courseRequestDto,Teacher teacher) {
        this.courseName = courseRequestDto.getCourseName();
        this.price = courseRequestDto.getPrice();
        this.description = courseRequestDto.getDescription();
        this.category = courseRequestDto.getCategory();
        this.teacher = teacher;
        this.dateAdded = courseRequestDto.getDateAdded();;
    }

    public void update(CourseRequestDto courseRequestDto) {
        this.courseName = courseRequestDto.getCourseName();
        this.price = courseRequestDto.getPrice();
        this.description = courseRequestDto.getDescription();
        this.category = courseRequestDto.getCategory();
    }
}



