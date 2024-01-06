package com.sparta.admin.service;


import com.sparta.admin.dto.LoginRequestDto;
import com.sparta.admin.dto.SignupRequestDto;
import com.sparta.admin.dto.SignupResponseDto;
import com.sparta.admin.entity.Admin;
import com.sparta.admin.entity.AdminRoleEnum;
import com.sparta.admin.jwt.JwtUtil;
import com.sparta.admin.repository.AdminRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final String MANAGER_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }



    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        System.out.println("1");
        //회원 중복 확인 -> 이메일
        Optional<Admin> checkEmail = adminRepository.findByEmail(email);
        if (checkEmail.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        System.out.println("2");
        AdminRoleEnum role = null;
        String department = signupRequestDto.getDepartment();
        if ("curriculum".equals(department) || "development".equals(department)){
            role = AdminRoleEnum.MANAGER;
        }else if("marketing".equals(department)){
            role = AdminRoleEnum.STAFF;
        }else{
            throw new IllegalArgumentException("잘못된 부서 입니다.");
        }
        System.out.println("3");
        Admin admin = new Admin(email,password, department,role);
        adminRepository.save(admin);
        return new SignupResponseDto(admin);
    }

    public String login(LoginRequestDto loginRequestDto, HttpServletResponse res) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        Admin admin = adminRepository.findByEmail(email).orElseThrow(
                ()-> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        if (!passwordEncoder.matches(password, admin.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //JWT 생성 및 쿠키에 저장 후 response 객체에 추가
        String token = jwtUtil.createToken(admin.getEmail(), admin.getRole());
        jwtUtil.addJwtToCookie(token,res);
        return token;
    }
}
