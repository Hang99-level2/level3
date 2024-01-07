package com.sparta.admin.filter;


import com.sparta.admin.entity.Admin;
import com.sparta.admin.jwt.JwtUtil;
import com.sparta.admin.repository.AdminRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@Order(1)
public class AuthFilter implements Filter {

    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;

    public AuthFilter(AdminRepository adminRepository, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        //인증 필요 없음
        if (StringUtils.hasText(url) &&
                (url.startsWith("/api/admins") || url.startsWith("/api/login"))
        ) {
            // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
            chain.doFilter(request, response);

        }
        else{
            // 나머지 API 요청은 인증 처리 진행
            //토큰 가져옴
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring
                String token = jwtUtil.substringToken(tokenValue);

                // 토큰 검증
                if (!jwtUtil.validateToken(token)) {
                    throw new IllegalArgumentException("Token Error");
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);

                Admin admin = adminRepository.findByEmail(info.getSubject()).orElseThrow(() ->
                        new NullPointerException("Not Found Admin")
                );
                request.setAttribute("role", admin.getRole());
                chain.doFilter(request, response);
            } else {
                throw new IllegalArgumentException("Not Found Token");
            }
        }
    }

}