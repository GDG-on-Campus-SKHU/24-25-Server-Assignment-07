package com.example.gdg_homework.dto;

import com.example.gdg_homework.domain.Schedule;
import com.example.gdg_homework.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ScheduleDto {

    @Getter
    @Builder
    public static class Request {
        private String title;
        private String description;
        private String location;

        public Schedule toEntity(User user) {
            return Schedule.builder()
                    .title(title)
                    .description(description)
                    .location(location)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private String location;

        public static Response from(Schedule schedule) {
            return Response.builder()
                    .id(schedule.getId())
                    .title(schedule.getTitle())
                    .description(schedule.getDescription())
                    .location(schedule.getLocation())
                    .build();
        }
    }
}
