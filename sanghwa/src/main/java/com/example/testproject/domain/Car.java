package com.example.testproject.domain;

import com.example.testproject.dto.CarSaveDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private String ownerName;

    @Builder
    public Car(String brand, String model, String ownerName) {
        this.brand = brand;
        this.model = model;
        this.ownerName = ownerName;
    }

    public void update(CarSaveDto carSaveDto) {
        this.brand = carSaveDto.getBrand();
        this.model = carSaveDto.getModel();
        this.ownerName = carSaveDto.getOwnerName();
    }
}