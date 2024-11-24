package com.gdg.googleloginexample.service;

import com.gdg.googleloginexample.domain.Restaurant;
import com.gdg.googleloginexample.domain.Review;
import com.gdg.googleloginexample.domain.User;
import com.gdg.googleloginexample.dto.ReviewInfoDto;
import com.gdg.googleloginexample.dto.ReviewSaveRequestDto;
import com.gdg.googleloginexample.repository.RestaurantRepository;
import com.gdg.googleloginexample.repository.ReviewRepository;
import com.gdg.googleloginexample.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public ReviewInfoDto createReview(Principal principal, ReviewSaveRequestDto reviewSaveRequestDto) {
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User not found"));

        Restaurant restaurant= restaurantRepository.findById(reviewSaveRequestDto.getRestaurantId())
                .orElseThrow(()-> new IllegalArgumentException("Restaurant not found"));


        Review savedReview = reviewRepository.save(Review.builder()
                .user(user)
                .restaurant(restaurant)
                .comment(reviewSaveRequestDto.getComment())
                .rating(reviewSaveRequestDto.getRating())
                .build());

        return ReviewInfoDto.from(savedReview);
    }
    @Transactional(readOnly = true)
    public ReviewInfoDto getReview(Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new IllegalArgumentException("Review not found"));

        return ReviewInfoDto.from(review);
    }

    @Transactional
    public ReviewInfoDto updateReview(Principal principal, Long reviewId, ReviewInfoDto reviewInfoDto) {

        Long userId = Long.parseLong(principal.getName());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new IllegalArgumentException("Review not found"));

        if(!review.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("User not authorized");
        }
        review.upadate(reviewInfoDto);

        return ReviewInfoDto.from(review);
    }

    @Transactional
    public void deleteReview(Principal principal, long reviewId) {
        Long userId = Long.parseLong(principal.getName());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new IllegalArgumentException("Review not found"));

        if(review.getUser().getId() != userId){
            throw new IllegalArgumentException("User not authorized");
        }
        reviewRepository.delete(review);
    }

}