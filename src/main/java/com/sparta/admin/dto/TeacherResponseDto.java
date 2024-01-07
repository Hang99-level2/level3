package com.sparta.admin.dto;

import com.sparta.admin.entity.Teacher;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherResponseDto {
    private String name;
    private int experience;
    private String company;
    private String phone;
    private String introduction;

    public TeacherResponseDto(Teacher teacher) {
        this.name = teacher.getName();
        this.experience = teacher.getExperience();
        this.company = teacher.getCompany();
        this.phone = teacher.getPhone();
        this.introduction = teacher.getIntroduction();
    }
}
