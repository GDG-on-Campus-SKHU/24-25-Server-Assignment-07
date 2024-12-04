package com.example.mydatecourse.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DateCourseListResponseDto {
    List<DateCourseResponseDto> dateCourseResponseDtoList;

    public static DateCourseListResponseDto from(List<DateCourseResponseDto> dateCourseResponseDtoList) {
        return DateCourseListResponseDto.builder()
                .dateCourseResponseDtoList(dateCourseResponseDtoList)
                .build();
    }
}
