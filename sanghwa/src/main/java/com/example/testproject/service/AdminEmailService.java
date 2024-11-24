package com.example.testproject.service;

import com.example.testproject.domain.AdminEmail;
import com.example.testproject.repository.AdminEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminEmailService {

    private final AdminEmailRepository adminEmailRepository;

    public void saveAdminEmail(String email) {
        AdminEmail adminEmail = new AdminEmail();
        adminEmail.builder()
                .email(email)
                .build();
        adminEmailRepository.save(adminEmail);
    }
}
