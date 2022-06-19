package com.yapp.lonessum.domain.dating.service;

import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.dating.repository.DatingSurveyRepository;
import com.yapp.lonessum.mapper.DatingSurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DatingSurveyService {

    private final DatingSurveyMapper datingSurveyMapper;

    private final DatingSurveyRepository datingSurveyRepository;

    @Transactional
    public Long createSurvey(DatingSurveyDto datingSurveyDto) {
        // 토큰에서 userId 가져옴
        Long userId = null;
//        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        DatingSurveyEntity datingSurvey = datingSurveyMapper.toEntity(datingSurveyDto);
//        meetingSurvey.setUser(user);
        return datingSurveyRepository.save(datingSurvey).getId();
    }

    @Transactional(readOnly = true)
    public DatingSurveyDto readSurvey(Long surveyId) {
        DatingSurveyEntity datingSurvey = datingSurveyRepository.findById(surveyId).orElseThrow(() -> new RuntimeException("존재하지 않는 설문입니다."));
        return datingSurveyMapper.toDto(datingSurvey);
    }

    @Transactional
    public Long updateSurvey(Long surveyId, DatingSurveyDto datingSurveyDto) {
        DatingSurveyEntity datingSurvey = datingSurveyRepository.findById(surveyId).orElseThrow(() -> new RuntimeException("존재하지 않는 설문입니다."));
        datingSurveyMapper.updateFromDto(datingSurveyDto, datingSurvey);
        return datingSurvey.getId();
    }

    @Transactional
    public Long deleteSurvey(Long surveyId) {
        DatingSurveyEntity datingSurvey = datingSurveyRepository.findById(surveyId).orElseThrow(() -> new RuntimeException("존재하지 않는 설문입니다."));
        datingSurveyRepository.delete(datingSurvey);
        return datingSurvey.getId();
    }
}
