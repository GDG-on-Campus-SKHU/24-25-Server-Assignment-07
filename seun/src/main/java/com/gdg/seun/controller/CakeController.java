package com.gdg.seun.controller;

import com.gdg.seun.domain.Cake;
import com.gdg.seun.dto.CakeDto;
import com.gdg.seun.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cakes")
public class CakeController {

    private final CakeService cakeService;

    @PostMapping
    public ResponseEntity<String> addCake(@RequestBody CakeDto cakeDto) {
        cakeService.addCake(cakeDto);
        return ResponseEntity.ok("케이크가 추가되었습니다.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cake> getCakeById(@PathVariable Long id) {
        Cake cake = cakeService.getCakeById(id);
        return ResponseEntity.ok(cake);
    }

    @GetMapping
    public ResponseEntity<List<Cake>> getAllCakes() {
        List<Cake> cakes = cakeService.getAllCakes();
        return ResponseEntity.ok(cakes);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCake(
            @PathVariable Long id,
            @RequestBody(required = false) String name,
            @RequestBody(required = false) Long price) {
        cakeService.updateCake(id, name, price);
        return ResponseEntity.ok("케이크가 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCakeById(@PathVariable Long id) {
        cakeService.removeCakeById(id);
        return ResponseEntity.ok("케이크가 삭제되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<String> removeAllCakes() {
        cakeService.removeAllCakes();
        return ResponseEntity.ok("모든 케이크가 삭제되었습니다.");
    }
}
