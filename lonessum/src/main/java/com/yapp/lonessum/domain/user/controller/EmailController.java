package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.user.dto.AuthCodeRequest;
import com.yapp.lonessum.domain.user.dto.EmailRequest;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.EmailService;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<LocalDateTime> updateAndSendEmail(@RequestHeader(value = "Authorization") String token, @RequestBody EmailRequest emailRequest) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(emailService.updateAndSendEmail(user, emailRequest.getEmail()));
    }

    @PutMapping
    public ResponseEntity<Boolean> authenticateWithEmail(@RequestHeader(value = "Authorization") String token,
                                                  @RequestBody AuthCodeRequest authCodeRequest) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(emailService.authenticateWithEmail(user, authCodeRequest.getAuthCode()));
    }

    @PostMapping("/test")
    public ResponseEntity testEmail(@RequestHeader(value = "Authorization") String token, @RequestBody EmailRequest emailRequest) {
        emailService.sendAuthCode(emailRequest.getEmail(), "this is test code");
        return ResponseEntity.ok().build();
    }
}
