package com.yapp.lonessum.domain.dating.controller;

import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;
import com.yapp.lonessum.domain.dating.service.DatingSurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dating/survey")
public class DatingSurveyController {

    private final DatingSurveyService datingSurveyService;

    @PostMapping
    public ResponseEntity<Long> createSurvey(@RequestBody DatingSurveyDto datingSurveyDto) {
        return ResponseEntity.ok(datingSurveyService.createSurvey(datingSurveyDto));
    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<DatingSurveyDto> readSurvey(@PathVariable Long surveyId) {
        return ResponseEntity.ok(datingSurveyService.readSurvey(surveyId));
    }

    @PutMapping("/{surveyId}")
    public ResponseEntity<Long> updateSurvey(@PathVariable Long surveyId, @RequestBody DatingSurveyDto datingSurveyDto) {
        return ResponseEntity.ok(datingSurveyService.updateSurvey(surveyId, datingSurveyDto));
    }

    @DeleteMapping("/{surveyId}")
    public ResponseEntity<Long> deleteSurvey(@PathVariable Long surveyId) {
        return ResponseEntity.ok(datingSurveyService.deleteSurvey(surveyId));
    }
}
