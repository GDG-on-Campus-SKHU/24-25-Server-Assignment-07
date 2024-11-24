package com.gdg.googleloginexample.repository;


import com.gdg.googleloginexample.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
