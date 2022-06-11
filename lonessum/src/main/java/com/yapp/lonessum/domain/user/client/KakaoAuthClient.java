package com.yapp.lonessum.domain.user.client;

import com.yapp.lonessum.domain.user.dto.KakaoTokenRequest;
import com.yapp.lonessum.domain.user.dto.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "kakao-auth",
        url = "https://kauth.kakao.com",
        configuration = KakaoFeignConfig.class
)
public interface KakaoAuthClient {
    @PostMapping(
            path = "/oauth/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenResponse getToken(KakaoTokenRequest kakaoTokenRequest);
}
