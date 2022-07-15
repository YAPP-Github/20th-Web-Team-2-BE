package com.yapp.lonessum.domain.meeting.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.meeting.dto.MatchResultDto;
import com.yapp.lonessum.domain.meeting.service.MeetingMatchingService;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting/matching")
public class MeetingMatchingController {

    private final JwtService jwtService;
    private final UserService userService;
    private final MeetingMatchingService meetingmatchingService;

    @GetMapping
    public ResponseEntity<MatchResultDto> getMatchResult(@RequestHeader(value = "Authorization") String token) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(meetingmatchingService.getMatchResult(user));
    }
}
