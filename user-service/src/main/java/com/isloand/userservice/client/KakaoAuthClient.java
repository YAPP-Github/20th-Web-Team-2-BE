package com.isloand.userservice.client;

import com.isloand.userservice.dto.KakaoTokenRequest;
import com.isloand.userservice.dto.KakaoTokenResponse;
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
