package com.gdg.googleloginexample.domain;

import com.gdg.googleloginexample.dto.ReviewInfoDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String rating;

    @Builder
    public Review(User user, Restaurant restaurant, String comment, String rating) {
        this.user = user;
        this.restaurant = restaurant;
        this.comment = comment;
        this.rating = rating;
    }

    public void upadate(ReviewInfoDto reviewInfoDto) {
        this.comment = reviewInfoDto.getComment();
        this.rating = reviewInfoDto.getRating();
    }
}

