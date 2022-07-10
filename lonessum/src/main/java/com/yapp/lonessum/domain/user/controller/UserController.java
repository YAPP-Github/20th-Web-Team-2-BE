package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.domain.user.client.KakaoApiClient;
import com.yapp.lonessum.domain.user.dto.KakaoTokenInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final KakaoApiClient kakaoApiClient;

    //TODO : 이후에 제거
    //token 바탕 사용자 id 얻어오는 사용 예시
    @GetMapping("/id")
    public KakaoTokenInfoResponse getKakaoId(@RequestHeader(value = "Authorization") String token) {
        return kakaoApiClient.getTokenInfo(token);
    }
}
