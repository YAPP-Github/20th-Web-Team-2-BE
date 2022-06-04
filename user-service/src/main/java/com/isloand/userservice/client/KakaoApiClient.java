package com.isloand.userservice.client;

import com.isloand.userservice.dto.KakaoTokenInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "kakao-api",
        url = "https://kapi.kakao.com",
        configuration = KakaoFeignConfig.class
)
public interface KakaoApiClient {
    @GetMapping("/v1/user/access_token_info")
    KakaoTokenInfoResponse getTokenInfo(@RequestHeader("Authorization") String bearerToken);
}
