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

        if (user.getIsAdult() == null || user.getIsAdult() == false) {
            throw new RestApiException(UserErrorCode.AGE_TOO_YOUNG);
        }

        DatingSurveyEntity datingSurvey = user.getDatingSurvey();
        // 설문을 작성한 적 있으면 -> 기존 설문 수정
        if (datingSurvey != null) {
            datingSurveyMapper.updateFromDto(datingSurveyDto, datingSurvey);
            // 매칭 대기 상태로 등록
            datingSurvey.changeMatchStatus(MatchStatus.WAITING);
            return datingSurvey.getId();
        }
        // 설문을 작성한 적 없으면 -> 새로운 설문 추가
        else {
            DatingSurveyEntity newDatingSurvey = datingSurveyRepository.save(datingSurveyMapper.toEntity(datingSurveyDto));
            user.changeDatingSurvey(newDatingSurvey);
            // 매칭 대기 상태로 등록
            newDatingSurvey.changeMatchStatus(MatchStatus.WAITING);
            return newDatingSurvey.getId();
        }
    }

    @Transactional
    public Long rematchSurvey(UserEntity user) {
        DatingSurveyEntity datingSurvey = user.getDatingSurvey();
        if (datingSurvey == null) {
            throw new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY);
        }
        datingSurvey.changeMatchStatus(MatchStatus.WAITING);
        return datingSurvey.getId();
    }

    @Transactional(readOnly = true)
    public DatingSurveyDto readSurvey(UserEntity user) {
        DatingSurveyEntity datingSurvey = user.getDatingSurvey();
        if (datingSurvey == null) {
            throw new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY);
        }
        return datingSurveyMapper.toDto(datingSurvey);
    }

    @Transactional
    public Long updateSurvey(UserEntity user, DatingSurveyDto datingSurveyDto) {
        DatingSurveyEntity datingSurvey = user.getDatingSurvey();
        if (datingSurvey == null) {
            throw new RestApiException(SurveyErrorCode.NO_EXISTING_SURVEY);
        }
        datingSurveyDto.setId(datingSurvey.getId());
        datingSurveyMapper.updateFromDto(datingSurveyDto, datingSurvey);
        return datingSurvey.getId();
    }
}
