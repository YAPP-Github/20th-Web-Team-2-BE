package com.yapp.lonessum.domain.dating.controller;

import com.yapp.lonessum.config.jwt.JwtService;
import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;
import com.yapp.lonessum.domain.dating.dto.MyDatingSurveyDto;
import com.yapp.lonessum.domain.dating.service.DatingSurveyService;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dating/survey")
public class DatingSurveyController {

    private final JwtService jwtService;
    private final DatingSurveyService datingSurveyService;
    private final Logger logger = LoggerFactory.getLogger(DatingSurveyController.class);

    @PostMapping
    public ResponseEntity<Long> createSurvey(@RequestBody DatingSurveyDto datingSurveyDto) {
        UserEntity user = jwtService.getUserFromJwt();
        logger.info("User({}, {}) create dating survey", user.getId(), user.getUniversityEmail());
        return ResponseEntity.ok(datingSurveyService.createSurvey(user, datingSurveyDto));
    }

    @PostMapping("/rematch")
    public ResponseEntity<Long> rematchSurvey() {
        UserEntity user = jwtService.getUserFromJwt();
        logger.info("User({}, {}) rematch dating survey", user.getId(), user.getUniversityEmail());
        return ResponseEntity.ok(datingSurveyService.rematchSurvey(user));
    }

    @GetMapping
    public ResponseEntity<MyDatingSurveyDto> readSurvey() {
        UserEntity user = jwtService.getUserFromJwt();
        return ResponseEntity.ok(datingSurveyService.readSurvey(user));
    }

    @PatchMapping
    public ResponseEntity<Long> updateSurvey(@RequestBody DatingSurveyDto datingSurveyDto) {
        UserEntity user = jwtService.getUserFromJwt();
        logger.info("User({}, {}) update dating survey", user.getId(), user.getUniversityEmail());
        return ResponseEntity.ok(datingSurveyService.updateSurvey(user, datingSurveyDto));
    }

    @PutMapping("/rollback")
    public ResponseEntity rollBackToWaiting() {
        datingSurveyService.rollBackToWaiting();
        return ResponseEntity.ok().build();
    }
}
