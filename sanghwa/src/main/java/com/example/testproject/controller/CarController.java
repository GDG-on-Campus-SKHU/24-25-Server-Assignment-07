package com.example.testproject.controller;

import com.example.testproject.domain.Car;
import com.example.testproject.dto.CarListResponseDto;
import com.example.testproject.dto.CarResponseDto;
import com.example.testproject.dto.CarSaveDto;
import com.example.testproject.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    //자동차 등록
    @PostMapping("/save")
    public ResponseEntity<CarResponseDto> save(@RequestBody CarSaveDto carSaveDto) {
        return new ResponseEntity<>(carService.save(carSaveDto), HttpStatus.OK);
    }

    //자동차 찾기 - admin 전용
    @GetMapping("/find/{id}")
    public ResponseEntity<CarResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(carService.findById(id), HttpStatus.OK);
    }

    //모든 자동차 찾기 - admin 전용
    @GetMapping("/findAll")
    public ResponseEntity<CarListResponseDto> findAll() {
        return new ResponseEntity<CarListResponseDto>(carService.findAll(), HttpStatus.CREATED);
    }

    //자동차 수정 - admin 전용
    @PatchMapping("/{id}")
    public ResponseEntity<CarResponseDto> updateById(@PathVariable Long id, @RequestBody CarSaveDto carSaveDto) {
        return new ResponseEntity<>(carService.updateCar(id, carSaveDto), HttpStatus.OK);
    }

    //자동차 삭제 - admin 전용
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}