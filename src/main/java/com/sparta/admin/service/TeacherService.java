package com.sparta.admin.service;

import com.sparta.admin.dto.CourseResponseDto;
import com.sparta.admin.dto.TeacherRequestDto;
import com.sparta.admin.dto.TeacherResponseDto;
import com.sparta.admin.entity.Course;
import com.sparta.admin.entity.Teacher;
import com.sparta.admin.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto) {
        Teacher teacher = new Teacher(teacherRequestDto);
        teacherRepository.save(teacher);
        return new TeacherResponseDto(teacher);
    }


    public Teacher getTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow();
    }

    @Transactional
    public TeacherResponseDto updateTeacher(Long teacherId, TeacherRequestDto teacherRequestDto) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        teacher.update(teacherRequestDto);
        return new TeacherResponseDto(teacher);
    }

    public List<CourseResponseDto> getCourseByTeacherId(Long teacherId) {
        Teacher teacher = getTeacher(teacherId);
        List<Course> courses = teacher.getCourses();
        List<CourseResponseDto> courseResponseDtoList = courses.stream()
                .sorted(Comparator.comparing(course -> LocalDate.parse(course.getDateAdded()), Comparator.reverseOrder()))
                .map(CourseResponseDto::new)
                .collect(Collectors.toList());

        return courseResponseDtoList;
    }
}
