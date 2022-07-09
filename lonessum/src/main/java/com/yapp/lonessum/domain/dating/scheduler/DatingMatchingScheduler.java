package com.yapp.lonessum.domain.dating.scheduler;

import com.yapp.lonessum.common.algorithm.MatchingInfo;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.dating.algorithm.DatingMatchingAlgorithm;
import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.dating.repository.DatingMatchingRepository;
import com.yapp.lonessum.domain.dating.repository.DatingSurveyRepository;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.mapper.DatingSurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatingMatchingScheduler {

    private final DatingSurveyMapper datingSurveyMapper;
    private final DatingSurveyRepository datingSurveyRepository;
    private final DatingMatchingRepository datingMatchingRepository;

    @Transactional
    @Scheduled(cron = "00 00 22 * * ?")
    public void runMatch() {
        List<DatingSurveyEntity> datingSurveyList = datingSurveyRepository.findAllByMatchStatus(MatchStatus.WAITING)
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_WAITING_SURVEY));

        List<DatingSurveyDto> datingSurveyDtoList = new ArrayList<>();
        for (DatingSurveyEntity ds : datingSurveyList) {
            ds.changeMatchStatus(MatchStatus.MATCHED);
            DatingSurveyDto datingSurveyDto = datingSurveyMapper.toDto(ds);
            datingSurveyDto.setId(ds.getId());
            datingSurveyDtoList.add(datingSurveyDto);
        }

        DatingMatchingAlgorithm datingMatchingAlgorithm = new DatingMatchingAlgorithm();
        List<MatchingInfo<DatingSurveyDto>> result = datingMatchingAlgorithm.getResult(datingSurveyDtoList);

        result.forEach(matchingInfo -> datingMatchingRepository.save(matchingInfo.toDatingMatchingEntity()));
    }
}
