package com.yapp.lonessum.domain.email.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.email.dto.EmailRequest;
import com.yapp.lonessum.domain.email.service.EmailService;
import com.yapp.lonessum.domain.email.dto.TestEmailRequest;
import com.yapp.lonessum.domain.user.dto.AuthCodeRequest;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final JwtService jwtService;
    private final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @PostMapping
    public ResponseEntity<LocalDateTime> updateAndSendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        UserEntity user = jwtService.getUserFromJwt();
        logger.info("User({}) send email", emailRequest.getEmail());
        return ResponseEntity.ok(emailService.updateAndSendEmail(user, emailRequest.getEmail()));
    }

    @PutMapping
    public ResponseEntity<Boolean> authenticateWithEmail(@RequestBody AuthCodeRequest authCodeRequest) {
        UserEntity user = jwtService.getUserFromJwt();
        logger.info("User({}) email authenticate", user.getUniversityEmail());

        return ResponseEntity.ok(emailService.authenticateWithEmail(user, authCodeRequest.getAuthCode()));
    }

    @PostMapping("/test/new-email")
    public ResponseEntity addTestEmail(@RequestBody TestEmailRequest testEmailRequest) {
        emailService.addTestEmail(testEmailRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test")
    public ResponseEntity testEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        emailService.sendAuthCode(emailRequest.getEmail(), "this is test code");
        return ResponseEntity.ok().build();
    }
}
