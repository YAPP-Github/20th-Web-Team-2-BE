package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.domain.user.client.KakaoApiClient;
import com.yapp.lonessum.domain.user.dto.JoinRequest;
import com.yapp.lonessum.domain.user.dto.KakaoTokenInfoResponse;
import com.yapp.lonessum.domain.user.dto.LoginRequest;
import com.yapp.lonessum.domain.user.dto.LoginResponse;
import com.yapp.lonessum.domain.user.repository.RedisRepository;
import com.yapp.lonessum.domain.user.service.BlackListService;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final KakaoApiClient kakaoApiClient;
    private final UserService userService;
    private final BlackListService blackListService;

    @PostMapping("/join")
    public ResponseEntity<Long> testJoin(@RequestBody JoinRequest joinRequest) {
        return ResponseEntity.ok(userService.testJoin(joinRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> testLogin(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.testLogin(loginRequest));
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest httpServletRequest) {
        String jwt = httpServletRequest.getHeader("jwt");
        String userId = (String)httpServletRequest.getAttribute("userId");

        blackListService.registerBlackList(userId, jwt);

        return ResponseEntity.ok().build();
    }

    //TODO : 이후에 제거
    //token 바탕 사용자 id 얻어오는 사용 예시
    @GetMapping("/id")
    public KakaoTokenInfoResponse getKakaoId(@RequestHeader(value = "Authorization") String token) {
        return kakaoApiClient.getTokenInfo(token);
    }
}
