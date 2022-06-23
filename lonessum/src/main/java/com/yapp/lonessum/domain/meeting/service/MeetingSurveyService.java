package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import com.yapp.lonessum.mapper.MeetingSurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MeetingSurveyService {

    private final MeetingSurveyMapper meetingSurveyMapper;

    private final MeetingSurveyRepository meetingSurveyRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createSurvey(MeetingSurveyDto meetingSurveyDto) {
        // 토큰에서 userId 가져옴
        Long userId = null;
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        if (!user.getIsAuthenticated()) {
            throw new RuntimeException("이메일 인증이 완료되지 않은 유저입니다.");
        }

        MeetingSurveyEntity meetingSurvey = meetingSurveyMapper.toEntity(meetingSurveyDto);
        meetingSurvey.changeUser(user);
        return meetingSurveyRepository.save(meetingSurvey).getId();
    }

    @Transactional(readOnly = true)
    public MeetingSurveyDto readSurvey(Long surveyId) {
        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findById(surveyId).orElseThrow(() -> new RuntimeException("존재하지 않는 설문입니다."));
        return meetingSurveyMapper.toDto(meetingSurvey);
    }

    @Transactional
    public Long updateSurvey(Long surveyId, MeetingSurveyDto meetingSurveyDto) {
        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findById(surveyId).orElseThrow(() -> new RuntimeException("존재하지 않는 설문입니다."));
        meetingSurveyMapper.updateFromDto(meetingSurveyDto, meetingSurvey);
        return meetingSurvey.getId();
    }

    @Transactional
    public Long deleteSurvey(Long surveyId) {
        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findById(surveyId).orElseThrow(() -> new RuntimeException("존재하지 않는 설문입니다."));
        meetingSurveyRepository.delete(meetingSurvey);
        return meetingSurvey.getId();
    }
}
