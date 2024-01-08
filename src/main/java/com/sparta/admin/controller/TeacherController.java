package com.sparta.admin.controller;

import com.sparta.admin.dto.CourseResponseDto;
import com.sparta.admin.dto.TeacherRequestDto;
import com.sparta.admin.dto.TeacherResponseDto;
import com.sparta.admin.entity.AdminRoleEnum;
import com.sparta.admin.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/teachers")
    public TeacherResponseDto createTeacher(@RequestBody TeacherRequestDto teacherRequestDto){
        return teacherService.createTeacher(teacherRequestDto);
    }

    @GetMapping("/teachers/{teacherId}")
    public TeacherResponseDto getTeacher(@PathVariable Long teacherId){
        return new TeacherResponseDto(teacherService.getTeacher(teacherId));
    }

    @PutMapping("/teachers/{teacherId}")
    public TeacherResponseDto updateTeacher(@PathVariable Long teacherId,
                                            @RequestBody TeacherRequestDto teacherRequestDto,
                                            HttpServletRequest req){
        AdminRoleEnum role = (AdminRoleEnum) req.getAttribute("role");
        if(AdminRoleEnum.STAFF.equals(role)){
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return teacherService.updateTeacher(teacherId,teacherRequestDto);
    }

    @GetMapping("/teachers/{teacherId}/courses")
    public List<CourseResponseDto> getCourseByTeacherId(@PathVariable Long teacherId){
        return teacherService.getCourseByTeacherId(teacherId);
    }
}
