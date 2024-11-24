package com.gdg.oauthlogin.repository;

import com.gdg.oauthlogin.domain.Product;
import com.gdg.oauthlogin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Predicate;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Predicate <Product> findByCategory(String category);

    Predicate <Product> findByUser(User user);
}
