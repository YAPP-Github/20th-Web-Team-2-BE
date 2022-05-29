package com.isloand.userservice.controller;

import com.isloand.userservice.service.EmailService;
import com.isloand.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/email")
    public void sendEmail() {
        // jwt 토큰에서 유저 정보 가져오기
        Long userId = userService.getUserIdFromJwt();

        // userId로 universityEmail 가져오기
        String universityEmail = userService.getUniversityEmailById(userId);

        emailService.sendEmail(universityEmail);
    }

    @PostMapping("/email")
    public void authenticateByEmail(String authCode) {
        emailService.authenticateByEmail(authCode);
    }
}
