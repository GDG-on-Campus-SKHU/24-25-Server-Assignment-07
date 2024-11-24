package com.example.testproject.repository;

import com.example.testproject.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
