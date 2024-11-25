package com.gdg.songin.product.service;



import com.gdg.songin.product.domain.Product;
import com.gdg.songin.product.dto.ProductInfoDto;
import com.gdg.songin.product.dto.ProductRequestDto;
import com.gdg.songin.product.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductInfoDto saveProduct(ProductRequestDto productRequestDto) {
        Product saveProduct = productRepository.save(Product.builder()
                .brand(productRequestDto.getBrand())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .stockQuantity(productRequestDto.getStockQuantity())
                .category(productRequestDto.getCategory())
                .build());
        return ProductInfoDto.from(saveProduct);

    }
    @Transactional(readOnly = true)
    public ProductInfoDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        return ProductInfoDto.from(product);
    }
    @Transactional
    public ProductInfoDto updateProduct(Long productId, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        product.update(productRequestDto);
        return ProductInfoDto.from(product);

    }
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        productRepository.delete(product);
    }
}
