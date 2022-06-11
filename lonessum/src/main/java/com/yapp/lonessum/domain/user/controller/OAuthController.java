package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.domain.user.client.KakaoAuthClient;
import com.yapp.lonessum.domain.user.dto.KakaoTokenRequest;
import com.yapp.lonessum.domain.user.dto.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final KakaoAuthClient kakaoAuthClient;

    @GetMapping("/kakao")
    public ResponseEntity<KakaoTokenResponse> getKakaoAuthorization(@RequestParam String code) {
        KakaoTokenRequest kakaoTokenRequest = makeTokenRequest(code);
        KakaoTokenResponse token = kakaoAuthClient.getToken(kakaoTokenRequest);

        return ResponseEntity.ok(token);
    }

    private KakaoTokenRequest makeTokenRequest(String code) {
        return KakaoTokenRequest.builder()
                .grant_type("authorization_code")
                .client_id("24608dc716988209e4f923e0a8f4c495")
                .redirect_uri("http://localhost:8080/oauth/kakao")
                .code(code)
                .client_secret("secret")
                .build();
    }
}
