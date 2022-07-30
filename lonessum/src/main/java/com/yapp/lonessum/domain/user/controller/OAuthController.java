package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.user.client.KakaoApiClient;
import com.yapp.lonessum.domain.user.client.KakaoAuthClient;
import com.yapp.lonessum.domain.user.dto.KakaoTokenRequest;
import com.yapp.lonessum.domain.user.dto.KakaoTokenResponse;
import com.yapp.lonessum.domain.user.dto.KakaoUserResponse;
import com.yapp.lonessum.domain.user.dto.LoginResponse;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.UserService;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthController {
    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoApiClient kakaoApiClient;
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/kakao")
    public ResponseEntity<LoginResponse> getKakaoAuthorization(@RequestParam String code) {
        KakaoTokenRequest kakaoTokenRequest = makeTokenRequest(code);
        KakaoTokenResponse token = kakaoAuthClient.getToken(kakaoTokenRequest);
        return ResponseEntity.ok(userService.login(token));
    }

    //TODO : secret은 배포 전 파일로부터 읽어오게 수정. 그전까지는 테스트할 때만 잠깐 넣고 push 전 제거
    private KakaoTokenRequest makeTokenRequest(String code) {
        return KakaoTokenRequest.builder()
                .grant_type("authorization_code")
                .client_id("24608dc716988209e4f923e0a8f4c495")
                .redirect_uri("https://lonessum.com/oauth/kakao")
//                 .redirect_uri("http://localhost:8080/oauth/kakao")
                .code(code)
                .client_secret("MB1m7saXKcZdz6CdHFP8zde4Y3Zooh6g")
                .build();
    }

    @Transactional
    @GetMapping("/kakao/age")
    public ResponseEntity<Boolean> getUserAgeFromKakao(@RequestParam String code, @RequestParam String type) {
        KakaoTokenRequest kakaoTokenRequest = makeTokenRequestForUserInfo(code, type);
        KakaoTokenResponse token = kakaoAuthClient.getToken(kakaoTokenRequest);

        KakaoUserResponse userInfo = kakaoApiClient.getUserInfo("Bearer " + token.getAccess_token());
        String age_range = userInfo.getKakao_account().getAge_range();

        if (age_range == null) {
            throw new RestApiException(UserErrorCode.NEED_AGE_AGREE);
        }
        String[] age = age_range.split("~");

        UserEntity user = jwtService.getUserFromJwt();
        if (Integer.parseInt(age[0]) < 20) {
            return ResponseEntity.ok(user.changeIsAdult(false));
//            throw new RestApiException(UserErrorCode.AGE_TOO_YOUNG);
        }
        return ResponseEntity.ok(user.changeIsAdult(true));
    }

    private KakaoTokenRequest makeTokenRequestForUserInfo(String code, String type) {
        return KakaoTokenRequest.builder()
                .grant_type("authorization_code")
                .client_id("24608dc716988209e4f923e0a8f4c495")
                .redirect_uri("https://lonessum.com/"+type+"/agreement")
                .code(code)
                .client_secret("MB1m7saXKcZdz6CdHFP8zde4Y3Zooh6g")
                .build();
    }
}
