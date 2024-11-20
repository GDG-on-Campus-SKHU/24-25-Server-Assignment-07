package com.meyame.itemstore.controller;

import com.meyame.itemstore.dto.request.useritem.UserItemDeleteReqDto;
import com.meyame.itemstore.dto.request.useritem.UserItemStoreReqDto;
import com.meyame.itemstore.dto.response.useritem.UserItemInfoResDto;
import com.meyame.itemstore.service.UserItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-items")
public class UserItemController {

    private final UserItemService userItemService;

    // 사용자가 아이템을 보관
    @PostMapping
    public ResponseEntity<UserItemInfoResDto> storeItem(@RequestBody UserItemStoreReqDto userItemStoreReqDto, Principal principal) {
        UserItemInfoResDto res = userItemService.storeItem(userItemStoreReqDto, Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // 사용자가 보관 중인 모든 아이템 목록 조회
    @GetMapping
    public ResponseEntity<List<UserItemInfoResDto>> findAllStoredItem(Principal principal) {
        List<UserItemInfoResDto> res = userItemService.findAllStoredItem(Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 사용자가 보관 중인 아이템 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteUserItem(@RequestBody UserItemDeleteReqDto userItemDeleteReqDto, Principal principal) {
        userItemService.deleteUserItem(userItemDeleteReqDto, Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
