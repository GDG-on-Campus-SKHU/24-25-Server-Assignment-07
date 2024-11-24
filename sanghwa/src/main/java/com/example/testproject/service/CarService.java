package com.example.testproject.service;

import com.example.testproject.domain.Car;
import com.example.testproject.dto.CarListResponseDto;
import com.example.testproject.dto.CarResponseDto;
import com.example.testproject.dto.CarSaveDto;
import com.example.testproject.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;

    @Transactional
    public CarResponseDto save(CarSaveDto carSaveDto) {
        Car car = carSaveDto.toEntity();
        carRepository.save(car);
        return CarResponseDto.from(car);
    }

    @Transactional
    public CarResponseDto findById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        return CarResponseDto.from(car);
    }

    @Transactional
    public CarListResponseDto findAll() {
        List<Car> cars = carRepository.findAll();
        List<CarResponseDto> carListResponseDtos = cars.stream()
                .map(CarResponseDto::from)
                .toList();
        return CarListResponseDto.from(carListResponseDtos);
    }

    @Transactional
    public CarResponseDto updateCar(Long id, CarSaveDto carSaveDto) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        car.update(carSaveDto);
        return CarResponseDto.from(car);
    }

    @Transactional
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

}
