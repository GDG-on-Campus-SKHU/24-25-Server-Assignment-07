package com.example.testproject.dto;

import com.example.testproject.domain.Car;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CarListResponseDto {
    private List<CarResponseDto> cars;

    public static CarListResponseDto from(List<CarResponseDto> cars){
        return CarListResponseDto.builder().cars(cars).build();
    }
}
