package com.gdg.oauthlogin.service;

import com.gdg.oauthlogin.domain.Product;
import com.gdg.oauthlogin.domain.User;
import com.gdg.oauthlogin.dto.ProductInfoDto;
import com.gdg.oauthlogin.dto.ProductSaveDto;
import com.gdg.oauthlogin.dto.UserInfo;
import com.gdg.oauthlogin.repository.ProductRepository;
import com.gdg.oauthlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final NormalLoginService normalLoginService;

    @Transactional
    public ProductInfoDto save(ProductSaveDto productSaveDto, Principal principal) {
        UserInfo user = normalLoginService.findByPrincipal(principal);

        Product product = Product.builder()
                .category(productSaveDto.getCategory())
                .name(productSaveDto.getName())
                .description(productSaveDto.getDescription())
                .price(productSaveDto.getPrice())
                .user(userRepository.findById(user.getId()).orElse(null))
                .build();

        productRepository.save(product);

        return ProductInfoDto.builder()
                .id(product.getId())
                .category(product.getCategory())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .publisherName(user.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public ProductInfoDto findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        return ProductInfoDto.builder()
                .id(product.getId())
                .category(product.getCategory())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .publisherName(product.getUser().getName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ProductInfoDto> findProductByCategory(String category) {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream()
                .filter(product -> product.getCategory().equals(category))
                .map(product -> new ProductInfoDto(
                        product.getId(),
                        product.getCategory(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getUser().getName()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductInfoDto> findProductByPublisherid(Long id) {
        List<Product> allProducts = productRepository.findAll();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("존재하지 않는 사용자압니다.")));

        return allProducts.stream()
                .filter(product -> product.getUser().getId().equals(user.getId()))
                .map(product -> new ProductInfoDto(
                        product.getId(),
                        product.getCategory(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getUser().getName()
                ))
                .toList();
    }

    @Transactional
    public ProductInfoDto updateProductById(Long id, ProductSaveDto productSaveDto, Principal principal) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        
        UserInfo user = normalLoginService.findByPrincipal(principal);

        if(!user.getId().equals(product.getUser().getId()))
            throw new IllegalArgumentException("수정할 권한이 없습니다.");

        return ProductInfoDto.builder()
                .id(product.getId())
                .category(productSaveDto.getCategory())
                .name(productSaveDto.getName())
                .description(productSaveDto.getDescription())
                .price(productSaveDto.getPrice())
                .publisherName(product.getUser().getName())
                .build();
    }

    @Transactional
    public void deleteProductById(Long id, Principal principal) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        UserInfo user = normalLoginService.findByPrincipal(principal);

        if(!user.getId().equals(product.getUser().getId()))
            throw new IllegalArgumentException("수정할 권한이 없습니다.");

        productRepository.deleteById(id);
    }
}
