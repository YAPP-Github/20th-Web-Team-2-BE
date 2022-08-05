package com.yapp.lonessum.domain.user.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.user.client.KakaoApiClient;
import com.yapp.lonessum.domain.user.dto.*;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.BlackListService;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final KakaoApiClient kakaoApiClient;
    private final JwtService jwtService;
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

    @GetMapping("/myInfo")
    public ResponseEntity<UserInfoDto> getMyInfo() {
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(userService.getMyInfo(user));
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest httpServletRequest) {
        String jwt = httpServletRequest.getHeader("Authorization");
        String userId = httpServletRequest.getAttribute("userId").toString();

        blackListService.registerBlackList(userId, jwt);

        logger.info("User({}) logout ", userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity withdraw() {
        UserEntity user = jwtService.getUserFromJwt();
        userService.withdraw(user.getId());

        logger.info("User({}, {}) withdraw ", user.getId(), user.getUniversityEmail());
        return ResponseEntity.ok().build();
    }

    //TODO : 이후에 제거
    //token 바탕 사용자 id 얻어오는 사용 예시
    @GetMapping("/id")
    public KakaoTokenInfoResponse getKakaoId(@RequestHeader(value = "Authorization") String token) {
        return kakaoApiClient.getTokenInfo(token);
    }
}
