package com.example.mydatecourse.service;

import com.example.mydatecourse.domain.DateCourse;
import com.example.mydatecourse.domain.User;
import com.example.mydatecourse.dto.DateCourseListResponseDto;
import com.example.mydatecourse.dto.DateCourseRequestDto;
import com.example.mydatecourse.dto.DateCourseResponseDto;
import com.example.mydatecourse.repository.DateCourseRepository;
import com.example.mydatecourse.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MyDateCourseService {
    private final DateCourseRepository dateCourseRepository;
    private final UserRepository userRepository;

    @Transactional
    public DateCourseResponseDto save(DateCourseRequestDto dateCourseRequestDto) {
        User user = userRepository.findById(dateCourseRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        DateCourse dateCourse = dateCourseRequestDto.toEntity(user);
        dateCourseRepository.save(dateCourse);
        return DateCourseResponseDto.from(dateCourse);
    }

    @Transactional
    public DateCourseResponseDto findDateCourse(Long id) {
        DateCourse dateCourse = dateCourseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 데이트코스입니다."));
        return DateCourseResponseDto.from(dateCourse);
    }

    @Transactional
    public DateCourseResponseDto updateDateCourse(Long id, DateCourseRequestDto dateCourseRequestDto) {
        DateCourse dateCourse = dateCourseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 데이트코스입니다."));
        dateCourse.update(dateCourseRequestDto.getTitle(), dateCourseRequestDto.getDescription(), dateCourseRequestDto.getLocation(), dateCourseRequestDto.getSeason());
        return DateCourseResponseDto.from(dateCourse);
    }

    @Transactional
    public void deleteDateCourse(Long id) {
        dateCourseRepository.deleteById(id);
    }

    @Transactional
    public DateCourseListResponseDto findAllDateCourse() {
        List<DateCourse> dateCourseList = dateCourseRepository.findAll();
        List<DateCourseResponseDto> dateCourseResponseDtos = dateCourseList.stream()
                .map(DateCourseResponseDto::from)
                .toList();
        return DateCourseListResponseDto.from(dateCourseResponseDtos);
    }
}
