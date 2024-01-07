package com.sparta.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherRequestDto {
    private String name;
    private int experience;
    private String company;
    private String phone;
    private String introduction;
}
