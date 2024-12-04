package com.gdg.oauthlogin.controller;

import com.gdg.oauthlogin.dto.ProductInfoDto;
import com.gdg.oauthlogin.dto.ProductSaveDto;
import com.gdg.oauthlogin.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TradeController {
    private final TradeService tradeService;

    @PostMapping("/upload")
    public ResponseEntity<ProductInfoDto> save(@RequestBody ProductSaveDto productSaveDto, Principal principal) {
        ProductInfoDto productInfoDto = tradeService.save(productSaveDto, principal);

        return ResponseEntity.ok(productInfoDto);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductInfoDto> findById(@PathVariable Long id) {
        ProductInfoDto productInfoDto = tradeService.findProductById(id);

        return ResponseEntity.ok(productInfoDto);
    }

    @GetMapping("/find/category/{category}")
    public ResponseEntity<List<ProductInfoDto>> findByCategory(@PathVariable String category) {
        List<ProductInfoDto> productInfoDtos = tradeService.findProductByCategory(category);

        return ResponseEntity.ok(productInfoDtos);
    }

    @GetMapping("/find/uploader/{userid}")
    public ResponseEntity<List<ProductInfoDto>> findByUploader(@PathVariable Long userid) {
        List<ProductInfoDto> productInfoDtos = tradeService.findProductByPublisherid(userid);

        return ResponseEntity.ok(productInfoDtos);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<ProductInfoDto> update(@PathVariable Long id, @RequestBody ProductSaveDto productSaveDto, Principal principal) {
        ProductInfoDto productInfoDto = tradeService.updateProductById(id, productSaveDto, principal);

        return ResponseEntity.ok(productInfoDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id, Principal principal) {
        tradeService.deleteProductById(id, principal);

        return ResponseEntity.ok().build();
    }
}
