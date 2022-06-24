package com.yapp.lonessum.domain.meeting.controller;

import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.service.MeetingSurveyService;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting/survey")
public class MeetingSurveyController {

    private final UserService userService;
    private final MeetingSurveyService meetingSurveyService;

    @PostMapping
    public ResponseEntity<Long> createSurvey(@RequestHeader(value = "Authorization") String token,
                                             @RequestBody MeetingSurveyDto meetingSurveyDto) {
        UserEntity user = userService.getUserFromToken(token);
        return ResponseEntity.ok(meetingSurveyService.createSurvey(user, meetingSurveyDto));
    }

    @GetMapping
    public ResponseEntity<MeetingSurveyDto> readSurvey(@RequestHeader(value = "Authorization") String token) {
        UserEntity user = userService.getUserFromToken(token);
        return ResponseEntity.ok(meetingSurveyService.readSurvey(user));
    }

    @PutMapping
    public ResponseEntity<Long> updateSurvey(@RequestHeader(value = "Authorization") String token,
                                             @RequestBody MeetingSurveyDto meetingSurveyDto) {
        UserEntity user = userService.getUserFromToken(token);
        return ResponseEntity.ok(meetingSurveyService.updateSurvey(user, meetingSurveyDto));
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteSurvey(@RequestHeader(value = "Authorization") String token) {
        UserEntity user = userService.getUserFromToken(token);
        return ResponseEntity.ok(meetingSurveyService.deleteSurvey(user));
    }
}
