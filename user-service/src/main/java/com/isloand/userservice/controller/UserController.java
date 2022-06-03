package com.isloand.userservice.controller;

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
}
