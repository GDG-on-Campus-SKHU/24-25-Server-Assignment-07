package com.meyame.itemstore.controller;

import com.meyame.itemstore.dto.request.item.ItemRegisterReqDto;
import com.meyame.itemstore.dto.request.item.ItemUpdateReqDto;
import com.meyame.itemstore.dto.response.item.ItemInfoResDto;
import com.meyame.itemstore.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    // 아이템 등록 (아직 사용자 보유 X)
    @PostMapping
    public ResponseEntity<ItemInfoResDto> registerItem(@RequestBody ItemRegisterReqDto itemRegisterReqDto) {
        ItemInfoResDto res = itemService.registerItem(itemRegisterReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // 아이템 조회
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemInfoResDto> findItemById(@PathVariable Long itemId) {
        ItemInfoResDto res = itemService.findItemById(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 현재 등록되어 있는 모든 아이템 조회
    @GetMapping
    public ResponseEntity<List<ItemInfoResDto>> findAllItems() {
        List<ItemInfoResDto> res = itemService.findAllItems();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 아이템 수정
    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemInfoResDto> updateItemById(@PathVariable Long itemId, @RequestBody ItemUpdateReqDto itemUpdateReqDto) {
        ItemInfoResDto res = itemService.updateItemById(itemId,itemUpdateReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 아이템 삭제
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItemById(@PathVariable Long itemId) {
        itemService.deleteItemById(itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
