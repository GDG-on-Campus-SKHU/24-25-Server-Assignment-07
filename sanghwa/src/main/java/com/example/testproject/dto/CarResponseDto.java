package com.example.testproject.dto;

import com.example.testproject.domain.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarResponseDto {
    private Long id;
    private String brand;
    private String model;
    private String ownerName;

    public static CarResponseDto from(Car car) {
        return CarResponseDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .ownerName(car.getOwnerName())
                .build();
    }
}