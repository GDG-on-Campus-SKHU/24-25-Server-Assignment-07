package com.gdg.songin.product.controller;


import com.gdg.songin.product.dto.ProductInfoDto;
import com.gdg.songin.product.dto.ProductRequestDto;
import com.gdg.songin.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductInfoDto> saveProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.saveProduct(productRequestDto);

        return ResponseEntity.ok().body(productService.saveProduct(productRequestDto));
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductInfoDto> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok().body(productService.getProduct(productId));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductInfoDto> updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok().body(productService.updateProduct(productId, productRequestDto));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductInfoDto> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
