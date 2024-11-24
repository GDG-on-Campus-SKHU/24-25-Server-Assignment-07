package com.example.gdg_homework.domain;

import com.example.gdg_homework.dto.ScheduleDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Schedule(String title, String description, String location, User user) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.user = user;
    }

    public void update(ScheduleDto.Request scheduleDto) {
        this.title = scheduleDto.getTitle();
        this.description = scheduleDto.getDescription();
        this.location = scheduleDto.getLocation();
    }
}
