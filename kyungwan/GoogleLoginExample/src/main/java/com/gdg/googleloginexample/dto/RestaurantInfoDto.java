package com.gdg.googleloginexample.dto;


import com.gdg.googleloginexample.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantInfoDto {
    Long id;
    String name;
    String master;

    public static RestaurantInfoDto from(Restaurant restaurant) {
        return RestaurantInfoDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .master(restaurant.getMaster())
                .build();
    }
}
