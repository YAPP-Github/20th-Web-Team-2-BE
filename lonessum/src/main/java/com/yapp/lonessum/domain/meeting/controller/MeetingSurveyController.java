package com.yapp.lonessum.domain.meeting.controller;

import com.yapp.lonessum.domain.meeting.dto.CreateSurveyReq;
import com.yapp.lonessum.domain.meeting.dto.ReadSurveyRes;
import com.yapp.lonessum.domain.meeting.dto.UpdateSurveyReq;
import com.yapp.lonessum.domain.meeting.service.MeetingSurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class MeetingSurveyController {

    private final MeetingSurveyService meetingSurveyService;

    @PostMapping
    public ResponseEntity<Long> createSurvey(@RequestBody CreateSurveyReq createSurveyReq) {
        return ResponseEntity.ok(meetingSurveyService.createSurvey(createSurveyReq));
    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<ReadSurveyRes> readSurvey(@PathVariable Long surveyId) {
        return ResponseEntity.ok(meetingSurveyService.readSurvey(surveyId));
    }

    @PutMapping("/{surveyId}")
    public ResponseEntity<Long> updateSurvey(@PathVariable Long surveyId, @RequestBody UpdateSurveyReq updateSurveyReq) {
        return ResponseEntity.ok(meetingSurveyService.updateSurvey(surveyId, updateSurveyReq));
    }

    @DeleteMapping("/{surveyId}")
    public Long deleteSurvey(@PathVariable Long surveyId) {
        return meetingSurveyService.deleteSurvey(surveyId);
    }
}
