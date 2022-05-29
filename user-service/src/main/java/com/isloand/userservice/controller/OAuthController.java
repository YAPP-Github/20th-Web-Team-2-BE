package com.isloand.userservice.controller;

import com.isloand.userservice.client.KakaoAuthClient;
import com.isloand.userservice.dto.KakaoTokenRequest;
import com.isloand.userservice.dto.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
                .client_secret("i3bd2fkzQJSkk19oDyJ6lrVTmcTd9UHD")
                .build();
    }
}
