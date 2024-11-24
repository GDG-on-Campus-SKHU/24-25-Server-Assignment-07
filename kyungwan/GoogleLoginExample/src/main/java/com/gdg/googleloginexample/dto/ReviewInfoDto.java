package com.gdg.googleloginexample.dto;

import com.gdg.googleloginexample.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewInfoDto {
    private Long id;
    private String username;
    private String name;
    private String comment;
    private String rating;

    public static ReviewInfoDto from(Review review) {
        return ReviewInfoDto.builder()
                .id(review.getId())
                .username(review.getUser().getUsername())
                .name(review.getRestaurant().getName())
                .comment(review.getComment())
                .rating(review.getRating())
                .build();
    }
}
