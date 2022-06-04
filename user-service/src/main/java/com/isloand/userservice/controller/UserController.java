package com.isloand.userservice.controller;

import com.isloand.userservice.client.KakaoApiClient;
import com.isloand.userservice.dto.KakaoTokenInfoResponse;
import com.isloand.userservice.service.EmailService;
import com.isloand.userservice.service.UniversityService;
import com.isloand.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final KakaoApiClient kakaoApiClient;
    private final UniversityService universityService;

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
