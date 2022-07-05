package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.domain.user.client.KakaoApiClient;
import com.yapp.lonessum.domain.user.client.KakaoAuthClient;
import com.yapp.lonessum.domain.user.dto.KakaoTokenRequest;
import com.yapp.lonessum.domain.user.dto.KakaoTokenResponse;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoApiClient kakaoApiClient;
    private final UserService userService;

    @GetMapping("/kakao")
    public ResponseEntity<KakaoTokenResponse> getKakaoAuthorization(@RequestParam String code) {
        KakaoTokenRequest kakaoTokenRequest = makeTokenRequest(code);
        KakaoTokenResponse token = kakaoAuthClient.getToken(kakaoTokenRequest);

        userService.login(token);

        return ResponseEntity.ok(token);
    }

    //TODO : secret은 배포 전 파일로부터 읽어오게 수정. 그전까지는 테스트할 때만 잠깐 넣고 push 전 제거
    private KakaoTokenRequest makeTokenRequest(String code) {
        return KakaoTokenRequest.builder()
                .grant_type("authorization_code")
                .client_id("24608dc716988209e4f923e0a8f4c495")
                .redirect_uri("http://49.50.175.112:8080/oauth/kakao")
                .code(code)
                .client_secret("MB1m7saXKcZdz6CdHFP8zde4Y3Zooh6g")
                .build();
    }
}
