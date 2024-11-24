package com.example.mydatecourse.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String location;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Season season;

    @Builder
    public DateCourse(String title, String description, String location, User user, Season season) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.user = user;
        this.season = season;
    }

    public void update(String title, String description, String location, Season season) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.season = season;
    }
}
