package com.yapp.lonessum.domain.dating.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;
import com.yapp.lonessum.domain.dating.service.DatingSurveyService;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/dating/survey")
public class DatingSurveyController {

    private final JwtService jwtService;
    private final UserService userService;
    private final DatingSurveyService datingSurveyService;

    @PostMapping
    public ResponseEntity<Long> createSurvey(@RequestHeader(value = "Authorization") String token,
                                             @RequestBody DatingSurveyDto datingSurveyDto) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(datingSurveyService.createSurvey(user, datingSurveyDto));
    }

    @PostMapping("/rematch")
    public ResponseEntity<Long> rematchSurvey(@RequestHeader(value = "Authorization") String token) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(datingSurveyService.rematchSurvey(user));
    }

    @GetMapping
    public ResponseEntity<DatingSurveyDto> readSurvey(@RequestHeader(value = "Authorization") String token) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(datingSurveyService.readSurvey(user));
    }

    @PutMapping
    public ResponseEntity<Long> updateSurvey(@RequestHeader(value = "Authorization") String token,
                                             @RequestBody DatingSurveyDto datingSurveyDto) {
//        UserEntity user = userService.getUserFromToken(token);
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(datingSurveyService.updateSurvey(user, datingSurveyDto));
    }
}
