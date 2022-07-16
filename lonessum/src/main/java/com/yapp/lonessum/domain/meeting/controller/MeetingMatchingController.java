package com.yapp.lonessum.domain.meeting.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.meeting.dto.MeetingMatchResultDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.service.MeetingMatchingService;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting/matching")
public class MeetingMatchingController {

    private final JwtService jwtService;
    private final UserService userService;
    private final MeetingMatchingService meetingmatchingService;

    @GetMapping
    public ResponseEntity<MeetingMatchResultDto> getMatchResult(@RequestHeader(value = "Authorization") String token) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(meetingmatchingService.getMatchResult(user));
    }

    @PostMapping
    public ResponseEntity<List<MeetingMatchingEntity>> testMatch(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok(meetingmatchingService.testMatch());
    }
}
