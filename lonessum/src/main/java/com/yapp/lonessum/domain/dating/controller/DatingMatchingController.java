package com.yapp.lonessum.domain.dating.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.dating.dto.DatingMatchResultDto;
import com.yapp.lonessum.domain.dating.dto.TestDatingMatchingResultDto;
import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.dating.service.DatingMatchingService;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dating/matching")
public class DatingMatchingController {

    private final JwtService jwtService;
    private final DatingMatchingService datingMatchingService;

    @GetMapping
    public ResponseEntity<DatingMatchResultDto> getMatchResult() {
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(datingMatchingService.getMatchResult(user));
    }

    @PostMapping
    public ResponseEntity<List<TestDatingMatchingResultDto>> testMatch() {
        return ResponseEntity.ok(datingMatchingService.testMatch());
    }
}
