package com.sparta.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String message;

    public LoginResponseDto(String message) {
        this.message = message;
    }
}
