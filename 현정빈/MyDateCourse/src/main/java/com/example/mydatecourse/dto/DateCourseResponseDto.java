package com.example.mydatecourse.dto;

import com.example.mydatecourse.domain.DateCourse;
import com.example.mydatecourse.domain.Role;
import com.example.mydatecourse.domain.Season;
import com.example.mydatecourse.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class DateCourseResponseDto {
    private Long id;
    private String title;
    private String description;
    private String locaion;
    private Long userId;
    private String season;

    public static DateCourseResponseDto from(DateCourse dateCourse) {
        return DateCourseResponseDto.builder()
                .id(dateCourse.getId())
                .title(dateCourse.getTitle())
                .description(dateCourse.getDescription())
                .locaion(dateCourse.getLocation())
                .userId(dateCourse.getUser().getId())
                .season(String.valueOf(dateCourse.getSeason()))
                .build();
    }
}
