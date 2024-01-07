package com.sparta.admin.dto;

import com.sparta.admin.entity.Admin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {
    private String email;
    private String department;

    public SignupResponseDto(Admin admin) {
        this.email = admin.getEmail();
        this.department = admin.getDepartment();
    }
}
