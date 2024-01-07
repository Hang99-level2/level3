package com.sparta.admin.controller;

import com.sparta.admin.dto.CourseRequestDto;
import com.sparta.admin.dto.CourseResponseDto;
import com.sparta.admin.entity.Admin;
import com.sparta.admin.entity.AdminRoleEnum;
import com.sparta.admin.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/courses")
    public CourseResponseDto createCourse(@RequestBody CourseRequestDto courseRequestDto){
        return this.courseService.createCourse(courseRequestDto);
    }

    @GetMapping("/courses/{courseId}")
    public CourseResponseDto getCourse(@PathVariable long courseId){
        return this.courseService.getCourse(courseId);
    }

    @PutMapping("/courses/{courseId}")
    public CourseResponseDto updateCourse(@PathVariable long courseId,
                                          @RequestBody CourseRequestDto courseRequestDto,
                                          HttpServletRequest req){
        AdminRoleEnum role = (AdminRoleEnum) req.getAttribute("role");
        if(AdminRoleEnum.STAFF.equals(role)){
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return this.courseService.updateCourse(courseId,courseRequestDto);
    }

    @GetMapping("/courses/category/{categoryName}")
    public List<CourseResponseDto> getCourseByCategory(@PathVariable String categoryName){
        return courseService.getCourseByCategory(categoryName);
    }
}
