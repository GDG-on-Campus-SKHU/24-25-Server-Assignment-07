package com.example.mydatecourse.dto;

import com.example.mydatecourse.domain.DateCourse;
import com.example.mydatecourse.domain.Season;
import com.example.mydatecourse.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DateCourseRequestDto {
    private String title;
    private String description;
    private String location;
    private Long userId;
    private Season season;

    public DateCourse toEntity(User user) {
        return DateCourse.builder()
                .title(title)
                .description(description)
                .location(location)
                .user(user)
                .season(season)
                .build();
    }
}
