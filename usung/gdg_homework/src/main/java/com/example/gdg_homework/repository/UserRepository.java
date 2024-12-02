package com.example.gdg_homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gdg_homework.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
