package com.yapp.lonessum.domain.meeting.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.meeting.dto.MeetingMatchResultDto;
import com.yapp.lonessum.domain.meeting.dto.TestMeetingMatchingResultDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.service.MeetingMatchingService;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting/matching")
public class MeetingMatchingController {

    private final JwtService jwtService;
    private final MeetingMatchingService meetingmatchingService;
    private final Logger logger = LoggerFactory.getLogger(MeetingMatchingController.class);

    @GetMapping
    public ResponseEntity<MeetingMatchResultDto> getMatchResult() {
        UserEntity user = jwtService.getUserFromJwt();
        logger.info("User({}, {}) get meeting match result", user.getId(), user.getUniversityEmail());
        return ResponseEntity.ok(meetingmatchingService.getMatchResult(user));
    }

    @PostMapping
    public ResponseEntity<List<TestMeetingMatchingResultDto>> testMatch() {
        return ResponseEntity.ok(meetingmatchingService.testMatch());
    }
}
