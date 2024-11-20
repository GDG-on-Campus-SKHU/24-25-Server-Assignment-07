package com.meyame.itemstore.controller;

import com.meyame.itemstore.dto.request.user.UserUpdateReqDto;
import com.meyame.itemstore.dto.response.auth.UserInfoDto;
import com.meyame.itemstore.dto.response.user.UserInfoResDto;
import com.meyame.itemstore.dto.response.user.UserUpdateResDto;
import com.meyame.itemstore.service.auth.GoogleLoginService;
import com.meyame.itemstore.service.auth.LocalLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final GoogleLoginService googleLoginService;
    private final LocalLoginService localLoginService;

    // 구글 로그인한 사용자 정보 조회
    @GetMapping("/google/profile")
    public UserInfoDto getGoogleUserInfo(Principal principal) {
        return googleLoginService.getGoogleUserInfo(principal);
    }

    // 자체 로그인한 사용자 정보 조회 - 로그인한 사용자가 자신의 정보 조회
    @GetMapping("/profile")
    public ResponseEntity<UserInfoResDto> getUserInfo(Principal principal) {
        UserInfoResDto res = localLoginService.getUserInfo(Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 자체 로그인한 사용자 정보 수정
    @PatchMapping("/profile")
    public ResponseEntity<UserUpdateResDto> updateUserInfo(@RequestBody UserUpdateReqDto userUpdateReqDto, Principal principal) {
        UserUpdateResDto res = localLoginService.updateUserInfo(userUpdateReqDto,Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 자체 로그인한 사용자 탈퇴
    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteUser(Principal principal) {
        localLoginService.deleteUser(Long.parseLong(principal.getName()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
