package com.yapp.lonessum.domain.meeting.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.service.MeetingSurveyService;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting/survey")
public class MeetingSurveyController {

    private final JwtService jwtService;
    private final UserService userService;
    private final MeetingSurveyService meetingSurveyService;

    /*
    * 매칭 시작하기 버튼
    * 설문을 처음부터 작성하는 경우
    * - 설문을 등록 or 수정하고
    * - 매칭에 참여
    * */
    @PostMapping
    public ResponseEntity<Long> createSurvey(@RequestHeader(value = "Authorization") String token,
                                             @RequestBody MeetingSurveyDto meetingSurveyDto) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(meetingSurveyService.createSurvey(user, meetingSurveyDto));
    }

    /*
     * 현재 설문으로 재매칭
     * */
    @PostMapping("/rematch")
    public ResponseEntity<Long> rematchSurvey(@RequestHeader(value = "Authorization") String token) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(meetingSurveyService.rematchSurvey(user));
    }

    @GetMapping
    public ResponseEntity<MeetingSurveyDto> readSurvey(@RequestHeader(value = "Authorization") String token) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(meetingSurveyService.readSurvey(user));
    }

    @PutMapping
    public ResponseEntity<Long> updateSurvey(@RequestHeader(value = "Authorization") String token,
                                             @RequestBody MeetingSurveyDto meetingSurveyDto) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(meetingSurveyService.updateSurvey(user, meetingSurveyDto));
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteSurvey(@RequestHeader(value = "Authorization") String token) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(meetingSurveyService.deleteSurvey(user));
    }
}
