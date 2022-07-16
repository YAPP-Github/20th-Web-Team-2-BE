package com.yapp.lonessum.domain.dating.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.dating.dto.DatingMatchResultDto;
import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.dating.service.DatingMatchingService;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dating/matching")
public class DatingMatchingController {

    private final JwtService jwtService;
    private final UserService userService;
    private final DatingMatchingService datingMatchingService;

    @GetMapping
    public ResponseEntity<DatingMatchResultDto> getMatchResult(@RequestHeader(value = "Authorization") String token) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(datingMatchingService.getMatchResult(user));
    }

    @PostMapping
    public ResponseEntity<List<DatingMatchingEntity>> testMatch(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(datingMatchingService.testMatch());
    }
}
