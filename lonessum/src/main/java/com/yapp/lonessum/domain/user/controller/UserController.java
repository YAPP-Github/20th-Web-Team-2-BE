package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.domain.user.client.KakaoApiClient;
import com.yapp.lonessum.domain.user.dto.KakaoTokenInfoResponse;
import com.yapp.lonessum.domain.user.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final KakaoApiClient kakaoApiClient;
    private final UniversityService universityService;

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
