package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.domain.meeting.dto.CreateSurveyReq;
import com.yapp.lonessum.domain.meeting.dto.ReadSurveyRes;
import com.yapp.lonessum.domain.meeting.dto.UpdateSurveyReq;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingSurveyService {

    private final MeetingSurveyRepository meetingSurveyRepository;

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
