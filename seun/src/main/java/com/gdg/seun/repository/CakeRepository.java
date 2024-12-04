package com.gdg.seun.repository;


import com.gdg.seun.domain.Cake;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CakeRepository extends JpaRepository<Cake, Long> {
}
