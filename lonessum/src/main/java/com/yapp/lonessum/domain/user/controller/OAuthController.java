package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.domain.user.client.KakaoApiClient;
import com.yapp.lonessum.domain.user.client.KakaoAuthClient;
import com.yapp.lonessum.domain.user.dto.KakaoTokenRequest;
import com.yapp.lonessum.domain.user.dto.KakaoTokenResponse;
import com.yapp.lonessum.domain.user.dto.KakaoUserResponse;
import com.yapp.lonessum.domain.user.service.UserService;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
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
                .redirect_uri("http://localhost:3000/oauth/kakao")
                .code(code)
                .client_secret("MB1m7saXKcZdz6CdHFP8zde4Y3Zooh6g")
                .build();
    }

    @GetMapping("/kakao/age")
    public ResponseEntity getUserAgeFromKakao(@RequestHeader(value = "Authorization") String token) {
        KakaoUserResponse userInfo = kakaoApiClient.getUserInfo("Bearer " + token);
        String age_range = userInfo.getKakao_account().getAge_range();
        if (age_range == null) {
            throw new RestApiException(UserErrorCode.NEED_AGE_AGREE);
        }
        String[] age = age_range.split("~");
        if (Integer.parseInt(age[0]) < 20) {
            userService.checkAdult(token, false);
            throw new RestApiException(UserErrorCode.AGE_TOO_YOUNG);
        }
        userService.checkAdult(token, true);
        return ResponseEntity.ok().build();
    }
}
