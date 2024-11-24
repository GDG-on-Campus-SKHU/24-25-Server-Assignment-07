package com.example.testproject.repository;

import com.example.testproject.domain.AdminEmail;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminEmailRepository extends JpaRepository<AdminEmail, Long> {
    public boolean existsByEmail(String email);

}
