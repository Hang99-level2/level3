package com.sparta.admin.service;

import com.sparta.admin.dto.CourseRequestDto;
import com.sparta.admin.dto.CourseResponseDto;
import com.sparta.admin.entity.Course;
import com.sparta.admin.entity.Teacher;
import com.sparta.admin.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final TeacherService teacherService;

    public CourseService(CourseRepository courseRepository, TeacherService teacherService) {
        this.courseRepository = courseRepository;
        this.teacherService = teacherService;
    }

    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto) {
        //카테고리 확인
        String category = courseRequestDto.getCategory();
        if (!"Spring".equals(category) && !"React".equals(category) && !"Node".equals(category)) {
            throw new IllegalArgumentException("카테고리가 맞지 않습니다.");
        }
        Teacher teacher = teacherService.getTeacher(courseRequestDto.getTeacherId());

        Course course = new Course(courseRequestDto,teacher);
        Course saveCourse = courseRepository.save(course);
        teacher.getCourses().add(course);
        return new CourseResponseDto(saveCourse);
    }

    public CourseResponseDto getCourse(long courseId) {
        //인증 확인

        Course course = courseRepository.findById(courseId).orElseThrow();
        return new CourseResponseDto(course);
    }

    @Transactional
    public CourseResponseDto updateCourse(long courseId, CourseRequestDto courseRequestDto) {
        //인증 확인
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.update(courseRequestDto);
        return new CourseResponseDto(course);
    }

    public List<CourseResponseDto> getCourseByCategory(String categoryName) {
        return courseRepository.findAllByCategoryOrderByDateAddedDesc(categoryName).stream().map(CourseResponseDto::new).toList();
    }
}
