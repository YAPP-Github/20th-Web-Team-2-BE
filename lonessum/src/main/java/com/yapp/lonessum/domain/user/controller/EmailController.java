package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.domain.user.dto.AuthCodeRequest;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.EmailService;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<LocalDateTime> updateAndSendEmail(@RequestHeader(value = "Authorization") String token, @RequestBody String email) {
        UserEntity user = userService.getUserFromToken(token);
        return ResponseEntity.ok(emailService.updateAndSendEmail(user, email));
    }

    @PutMapping
    public ResponseEntity<Boolean> authenticateWithEmail(@RequestHeader(value = "Authorization") String token,
                                                  @RequestBody AuthCodeRequest authCodeRequest) {
        UserEntity user = userService.getUserFromToken(token);
        return ResponseEntity.ok(emailService.authenticateWithEmail(user, authCodeRequest.getAuthCode()));
    }
}
