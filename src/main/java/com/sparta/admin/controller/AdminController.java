package com.sparta.admin.controller;
import com.sparta.admin.dto.LoginRequestDto;
import com.sparta.admin.dto.LoginResponseDto;
import com.sparta.admin.dto.SignupRequestDto;
import com.sparta.admin.dto.SignupResponseDto;
import com.sparta.admin.service.AdminService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admins")
    public SignupResponseDto signup(@Valid  @RequestBody SignupRequestDto signupRequestDto){
        return adminService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res){
        String token = adminService.login(loginRequestDto,res);
        if(token == null){
            return new LoginResponseDto("로그인 실패");
        }
        return new LoginResponseDto(token);
    }
}
