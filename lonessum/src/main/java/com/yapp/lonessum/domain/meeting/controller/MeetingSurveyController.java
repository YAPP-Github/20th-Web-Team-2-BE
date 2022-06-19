package com.yapp.lonessum.domain.meeting.controller;

import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.service.MeetingSurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting/survey")
public class MeetingSurveyController {

    private final MeetingSurveyService meetingSurveyService;

    @PostMapping
    public ResponseEntity<Long> createSurvey(@RequestBody MeetingSurveyDto meetingSurveyDto) {
        return ResponseEntity.ok(meetingSurveyService.createSurvey(meetingSurveyDto));
    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<MeetingSurveyDto> readSurvey(@PathVariable Long surveyId) {
        return ResponseEntity.ok(meetingSurveyService.readSurvey(surveyId));
    }

    @PutMapping("/{surveyId}")
    public ResponseEntity<Long> updateSurvey(@PathVariable Long surveyId, @RequestBody MeetingSurveyDto meetingSurveyDto) {
        return ResponseEntity.ok(meetingSurveyService.updateSurvey(surveyId, meetingSurveyDto));
    }

    @DeleteMapping("/{surveyId}")
    public ResponseEntity<Long> deleteSurvey(@PathVariable Long surveyId) {
        return ResponseEntity.ok(meetingSurveyService.deleteSurvey(surveyId));
    }
}
