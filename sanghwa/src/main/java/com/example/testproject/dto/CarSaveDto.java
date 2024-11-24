package com.example.testproject.dto;

import com.example.testproject.domain.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarSaveDto {
    private String brand;
    private String model;
    private String ownerName;

    public Car toEntity(){
        return Car.builder()
                .brand(brand)
                .model(model)
                .ownerName(ownerName)
                .build();
    }
}