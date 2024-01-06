package com.sparta.admin.entity;

import com.sparta.admin.dto.TeacherRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name="experience",nullable = false)
    private int experience;

    @Column(name= "company",nullable = false)
    private String company;

    @Column(name="phone",nullable = false)
    private String phone;

    @Column(name="introduction",nullable = false)
    private String introduction;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Course> courses;

    public Teacher(TeacherRequestDto teacherRequestDto) {
        this.name = teacherRequestDto.getName();
        this.experience = teacherRequestDto.getExperience();
        this.company = teacherRequestDto.getCompany();
        this.phone = teacherRequestDto.getPhone();
        this.introduction = teacherRequestDto.getIntroduction();
        this.courses = new ArrayList<>();
    }

    public void update(TeacherRequestDto teacherRequestDto) {
        this.experience = teacherRequestDto.getExperience();
        this.company = teacherRequestDto.getCompany();
        this.phone = teacherRequestDto.getPhone();
        this.introduction = teacherRequestDto.getIntroduction();
    }
}
