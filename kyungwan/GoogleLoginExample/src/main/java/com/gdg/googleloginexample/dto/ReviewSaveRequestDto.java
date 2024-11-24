package com.gdg.googleloginexample.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSaveRequestDto {
    Long restaurantId;
    String comment;
    String rating;

}

