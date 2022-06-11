package com.isoland.meetingservice.controller;

import com.isoland.meetingservice.dto.CreateSurveyReq;
import com.isoland.meetingservice.dto.ReadSurveyRes;
import com.isoland.meetingservice.dto.UpdateSurveyReq;
import com.isoland.meetingservice.service.MeetingSurveyService;
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
