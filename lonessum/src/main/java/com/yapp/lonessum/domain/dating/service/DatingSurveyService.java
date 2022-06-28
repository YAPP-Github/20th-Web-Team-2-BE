package com.yapp.lonessum.domain.dating.service;

import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.dating.repository.DatingSurveyRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.mapper.DatingSurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatingSurveyService {

    private final DatingSurveyMapper datingSurveyMapper;
    private final DatingSurveyRepository datingSurveyRepository;

    @Transactional
    public Long createSurvey(UserEntity user, DatingSurveyDto datingSurveyDto) {
        // 이메일 인증 검사
        if (!user.getIsAuthenticated()) {
            throw new RestApiException(UserErrorCode.NEED_EMAIL_AUTH);
        }

        Optional<DatingSurveyEntity> datingSurvey = datingSurveyRepository.findByUser(user);
        // 설문을 작성한 적 있으면 -> 기존 설문 수정
        if (datingSurvey.isPresent()) {
            datingSurveyMapper.updateFromDto(datingSurveyDto, datingSurvey.get());
            // 매칭 대기 상태로 등록
            datingSurvey.get().changeMatchStatus(MatchStatus.WAITING);
            return datingSurvey.get().getId();
        }
        // 설문을 작성한 적 없으면 -> 새로운 설문 추가
        else {
            DatingSurveyEntity newDatingSurvey = datingSurveyMapper.toEntity(datingSurveyDto);
            newDatingSurvey.changeUser(user);
            // 매칭 대기 상태로 등록
            newDatingSurvey.changeMatchStatus(MatchStatus.WAITING);
            return datingSurveyRepository.save(newDatingSurvey).getId();
        }
    }

    @Transactional
    public Long rematchSurvey(UserEntity user) {
        DatingSurveyEntity datingSurvey = datingSurveyRepository.findByUser(user)
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_EXIST_SURVEY));
        datingSurvey.changeMatchStatus(MatchStatus.WAITING);
        return datingSurvey.getId();
    }

    @Transactional(readOnly = true)
    public DatingSurveyDto readSurvey(UserEntity user) {
        DatingSurveyEntity datingSurvey = datingSurveyRepository.findByUser(user)
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_EXIST_SURVEY));
        return datingSurveyMapper.toDto(datingSurvey);
    }

    @Transactional
    public Long updateSurvey(UserEntity user, DatingSurveyDto datingSurveyDto) {
        DatingSurveyEntity datingSurvey = datingSurveyRepository.findByUser(user)
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_EXIST_SURVEY));
        datingSurveyMapper.updateFromDto(datingSurveyDto, datingSurvey);
        return datingSurvey.getId();
    }
}
