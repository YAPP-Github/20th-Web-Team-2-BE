package com.isoland.meetingservice.service;

import com.isoland.meetingservice.dto.SurveyRequest;
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
    public void addSurvey(SurveyRequest surveyRequest) {
        SurveyEntity survey = SurveyEntity.builder()
                .typeOfMeeting(surveyRequest.getTypeOfMeeting())
                .gender(surveyRequest.getGender())
                .averageAge(surveyRequest.getAverageAge())
                .ourUniversities(surveyRequest.getOurUniversities())
                .ourDepartments(surveyRequest.getOurDepartments())
                .averageHeight(surveyRequest.getAverageHeight())
                .avoidUniversities(surveyRequest.getAvoidUniversities())
                .preferUniversities(surveyRequest.getPreferUniversities())
                .preferAge(surveyRequest.getPreferAge())
                .preferHeight(surveyRequest.getPreferHeight())
                .preferDepartments(surveyRequest.getPreferDepartments())
                .mindSet(surveyRequest.getMindSet())
                .play(surveyRequest.getPlay())
                .isAbroad(surveyRequest.getIsAbroad())
                .domesticAreas(surveyRequest.getDomesticAreas())
                .abroadAreas(surveyRequest.getAbroadAreas())
                .channel(surveyRequest.getChannel())
                .agreement(surveyRequest.getAgreement())
                .kakaoId(surveyRequest.getKakaoId())
                .build();
        surveyRepository.save(survey);

        //user-service에 설문 등록 상황 전파 필요
    }
}
