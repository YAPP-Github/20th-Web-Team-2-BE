package com.isoland.meetingservice.service;

import com.isoland.meetingservice.dto.CreateSurveyReq;
import com.isoland.meetingservice.dto.ReadSurveyRes;
import com.isoland.meetingservice.dto.UpdateSurveyReq;
import com.isoland.meetingservice.entity.SurveyEntity;
import com.isoland.meetingservice.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Transactional
    public Long createSurvey(CreateSurveyReq createSurveyReq) {
        return null;
    }

    public ReadSurveyRes readSurvey(Long surveyId) {
        return null;
    }

    public Long updateSurvey(Long surveyId, UpdateSurveyReq updateSurveyReq) {
        return null;
    }

    public Long deleteSurvey(Long surveyId) {
        return null;
    }
}
