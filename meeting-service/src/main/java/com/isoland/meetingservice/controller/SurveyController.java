package com.isoland.meetingservice.controller;

import com.isoland.meetingservice.dto.SurveyRequest;
import com.isoland.meetingservice.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping
    public void addSurvey(@RequestBody SurveyRequest surveyRequest) {
        surveyService.addSurvey(surveyRequest);
    }
}
