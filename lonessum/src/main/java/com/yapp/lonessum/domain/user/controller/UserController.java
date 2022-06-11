package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.domain.user.client.KakaoApiClient;
import com.yapp.lonessum.domain.user.dto.AuthCodeRequest;
import com.yapp.lonessum.domain.user.dto.AuthCodeResponse;
import com.yapp.lonessum.domain.user.dto.KakaoTokenInfoResponse;
import com.yapp.lonessum.domain.user.service.EmailService;
import com.yapp.lonessum.domain.user.service.UniversityService;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final KakaoApiClient kakaoApiClient;
    private final UniversityService universityService;

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

    //TODO : 이후에 제거
    //token 바탕 사용자 id 얻어오는 사용 예시
    @GetMapping("/id")
    public KakaoTokenInfoResponse getKakaoId(@RequestHeader(value = "Authorization") String token) {
        return kakaoApiClient.getTokenInfo(token);
    }

    //TODO : 이후에 제거
    //대학교 정보 등록용 임시 api
    @PostMapping("/university")
    public void registerUniversityInfo() throws IOException {
        universityService.registerUniInfo();
    }
}
