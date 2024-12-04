package com.gdg.googleloginexample.repository;

import com.gdg.googleloginexample.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {}