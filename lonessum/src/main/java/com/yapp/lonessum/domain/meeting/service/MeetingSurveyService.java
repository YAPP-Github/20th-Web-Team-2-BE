package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import com.yapp.lonessum.exception.errorcode.MeetingErrorCode;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.mapper.MeetingSurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;

@Service
@RequiredArgsConstructor
public class MeetingSurveyService {

    private final MeetingSurveyMapper meetingSurveyMapper;
    private final MeetingSurveyRepository meetingSurveyRepository;

    @Transactional
    public Long createSurvey(UserEntity user, MeetingSurveyDto meetingSurveyDto) {
        if (!user.getIsAuthenticated()) {
            throw new RestApiException(UserErrorCode.NEED_EMAIL_AUTH);
        }
        MeetingSurveyEntity meetingSurvey = meetingSurveyMapper.toEntity(meetingSurveyDto);
        meetingSurvey.changeUser(user);
        return meetingSurveyRepository.save(meetingSurvey).getId();
    }

    @Transactional(readOnly = true)
    public MeetingSurveyDto readSurvey(UserEntity user) {
        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findByUserAndMatchStatus(user, MatchStatus.WAITING)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.ZERO_SURVEY));
        return meetingSurveyMapper.toDto(meetingSurvey);
    }

    @Transactional
    public Long updateSurvey(UserEntity user, MeetingSurveyDto meetingSurveyDto) {
        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findByUserAndMatchStatus(user, MatchStatus.WAITING)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.ZERO_SURVEY));
        meetingSurveyMapper.updateFromDto(meetingSurveyDto, meetingSurvey);
        return meetingSurvey.getId();
    }

    @Transactional
    public Long deleteSurvey(UserEntity user) {
        MeetingSurveyEntity meetingSurvey = meetingSurveyRepository.findByUserAndMatchStatus(user, MatchStatus.WAITING)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.ZERO_SURVEY));
        meetingSurveyRepository.delete(meetingSurvey);
        return meetingSurvey.getId();
    }
}
