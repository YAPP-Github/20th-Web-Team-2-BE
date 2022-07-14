package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.mapper.MeetingSurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingSurveyService {

    private final MeetingSurveyMapper meetingSurveyMapper;
    private final MeetingSurveyRepository meetingSurveyRepository;

    @Transactional
    public Long createSurvey(UserEntity user, MeetingSurveyDto meetingSurveyDto) {
        // 이메일 인증 검사
        if (!user.getIsAuthenticated()) {
            throw new RestApiException(UserErrorCode.NEED_EMAIL_AUTH);
        }

        if (user.getIsAdult() == null || user.getIsAdult() == false) {
            throw new RestApiException(UserErrorCode.AGE_TOO_YOUNG);
        }

        MeetingSurveyEntity meetingSurvey = user.getMeetingSurvey();
        // 설문을 작성한 적 있으면 -> 기존 설문 수정
        if (meetingSurvey != null) {
            meetingSurveyMapper.updateFromDto(meetingSurveyDto, meetingSurvey);
            // 매칭 대기 상태로 등록
            meetingSurvey.changeMatchStatus(MatchStatus.WAITING);
            return meetingSurvey.getId();
        }
        // 설문을 작성한 적 없으면 -> 새로운 설문 추가
        else {
            MeetingSurveyEntity newMeetingSurvey = meetingSurveyRepository.save(meetingSurveyMapper.toEntity(meetingSurveyDto));
            user.changeMeetingSurvey(newMeetingSurvey);
            // 매칭 대기 상태로 등록
            newMeetingSurvey.changeMatchStatus(MatchStatus.WAITING);
            return newMeetingSurvey.getId();
        }
    }

    @Transactional
    public Long rematchSurvey(UserEntity user) {
        MeetingSurveyEntity meetingSurvey = user.getMeetingSurvey();
        if (meetingSurvey == null) {
            throw new RestApiException(SurveyErrorCode.NO_EXIST_SURVEY);
        }
        meetingSurvey.changeMatchStatus(MatchStatus.WAITING);
        return meetingSurvey.getId();
    }

    @Transactional(readOnly = true)
    public MeetingSurveyDto readSurvey(UserEntity user) {
        MeetingSurveyEntity meetingSurvey = user.getMeetingSurvey();
        if (meetingSurvey == null) {
            throw new RestApiException(SurveyErrorCode.NO_EXIST_SURVEY);
        }
        return meetingSurveyMapper.toDto(meetingSurvey);
    }

    @Transactional
    public Long updateSurvey(UserEntity user, MeetingSurveyDto meetingSurveyDto) {
        MeetingSurveyEntity meetingSurvey = user.getMeetingSurvey();
        if (meetingSurvey == null) {
            throw new RestApiException(SurveyErrorCode.NO_EXIST_SURVEY);
        }
        meetingSurveyDto.setId(meetingSurvey.getId());
        meetingSurveyMapper.updateFromDto(meetingSurveyDto, meetingSurvey);
        return meetingSurvey.getId();
    }

    @Transactional
    public Long deleteSurvey(UserEntity user) {
        MeetingSurveyEntity meetingSurvey = user.getMeetingSurvey();
        if (meetingSurvey == null) {
            throw new RestApiException(SurveyErrorCode.NO_EXIST_SURVEY);
        }
        meetingSurveyRepository.delete(meetingSurvey);
        return meetingSurvey.getId();
    }
}
