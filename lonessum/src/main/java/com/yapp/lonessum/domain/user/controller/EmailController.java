package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.domain.user.dto.AuthCodeRequest;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.EmailService;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity sendEmail(@RequestHeader(value = "Authorization") String token, String email) {
        UserEntity user = userService.getUserFromToken(token);
        emailService.sendEmail(user, email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity authenticateWithEmail(@RequestHeader(value = "Authorization") String token,
                                                  @RequestBody AuthCodeRequest authCodeRequest) {
        UserEntity user = userService.getUserFromToken(token);
        emailService.authenticateWithEmail(user, authCodeRequest.getAuthCode());
        return ResponseEntity.ok().build();
    }
}
