package com.isloand.userservice.controller;

import com.isloand.userservice.client.KakaoApiClient;
import com.isloand.userservice.dto.KakaoTokenInfoResponse;
import com.isloand.userservice.dto.AuthCodeRequest;
import com.isloand.userservice.dto.AuthCodeResponse;
import com.isloand.userservice.service.EmailService;
import com.isloand.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final KakaoApiClient kakaoApiClient;

    @GetMapping("/email")
    public void sendEmail(String email) {

        // Jwt 토큰에서 유저정보 획득
        Long userId = 1L;

        // 유저 대학 이메일 정보 등록
        userService.updateUniversityEmail(userId, email);

        // 인증 이메일 발송
        emailService.sendEmail(userId, email);
    }

    @PostMapping("/email")
    public AuthCodeResponse authenticateWithEmail(@RequestBody AuthCodeRequest authCodeRequest) {

        // Jwt 토큰에서 유저정보 획득
        Long userId = 1L;

        // 인증토큰 검사
        return userService.authenticateWithEmail(userId, authCodeRequest.getAuthCode());
    }

    //token 바탕 사용자 id 얻어오는 사용 예시
    @GetMapping("/id")
    public KakaoTokenInfoResponse getKakaoId(@RequestHeader(value = "Authorization") String token) {
        return kakaoApiClient.getTokenInfo(token);
    }
}
