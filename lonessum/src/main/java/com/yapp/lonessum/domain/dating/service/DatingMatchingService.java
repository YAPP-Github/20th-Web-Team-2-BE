package com.yapp.lonessum.domain.dating.service;

import com.yapp.lonessum.common.algorithm.MatchingInfo;
import com.yapp.lonessum.domain.constant.DomesticArea;
import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.dating.algorithm.DatingMatchingAlgorithm;
import com.yapp.lonessum.domain.dating.dto.DatingMatchResultDto;
import com.yapp.lonessum.domain.dating.dto.DatingPartnerSurveyDto;
import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;
import com.yapp.lonessum.domain.dating.dto.TestDatingMatchingResultDto;
import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.dating.repository.DatingMatchingRepository;
import com.yapp.lonessum.domain.dating.repository.DatingSurveyRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.abroadArea.AbroadAreaService;
import com.yapp.lonessum.domain.university.UniversityService;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.mapper.DatingSurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatingMatchingService {

    private final UniversityService universityService;
    private final AbroadAreaService abroadAreaService;
    private final DatingSurveyRepository datingSurveyRepository;

    private final DatingSurveyMapper datingSurveyMapper;
    private final DatingMatchingRepository datingMatchingRepository;

    @Transactional
    public DatingMatchResultDto getMatchResult(UserEntity user) {

        DatingSurveyEntity datingSurvey = user.getDatingSurvey();
        DatingSurveyEntity partnerSurvey;

        if (datingSurvey == null) {
            // 작성한 설문이 없을 때
            return new DatingMatchResultDto(7000, SurveyErrorCode.NO_EXISTING_SURVEY.getMessage(), null, null);
        }

        // 작성한 설문이 있을 때
        // 매칭 대기중일 떄
        if (datingSurvey.getMatchStatus() == MatchStatus.WAITING) {
            return new DatingMatchResultDto(7001, SurveyErrorCode.WAITING_FOR_MATCH.getMessage(), null, null);
        }
        // 매칭 성공했을 때
        else if (datingSurvey.getMatchStatus() == MatchStatus.MATCHED) {
            // 내가 남자일 때
            if (datingSurvey.getGender() == Gender.MALE) {
                partnerSurvey = datingSurvey.getDatingMatching().getFemaleSurvey();
                // 내가 결제 안했을 때
                if (datingSurvey.getPayment() == null) {
                    return new DatingMatchResultDto(7002, SurveyErrorCode.PAY_FOR_MATCH.getMessage(), null, datingSurvey.getDatingMatching().getMatchedTime().plusDays(1L));
                }
            }
            // 내가 여자일 때
            else {
                partnerSurvey = datingSurvey.getDatingMatching().getMaleSurvey();
                // 상대가 결제 안했을 때
                if (partnerSurvey.getPayment() == null) {
                    return new DatingMatchResultDto(7003, SurveyErrorCode.WAITING_FOR_PAY.getMessage(), null, null);
                }
            }
            // 모두 결제했을 때 -> 매칭 상대 정보
            DatingPartnerSurveyDto datingPartnerSurveyDto = partnerSurvey.toPartnerSurveyDto();

            datingPartnerSurveyDto.setUniversity(partnerSurvey.getUser().getUniversity().getName());

            List<String> areaNames = new ArrayList<>();
            if (datingSurvey.getIsAbroad()) {
                areaNames = abroadAreaService.getAreaNameFromId(partnerSurvey.getAbroadAreas());
            }
            else {
                List<DomesticArea> domesticAreas = partnerSurvey.getDomesticAreas();
                for (DomesticArea da : domesticAreas) {
                    areaNames.add(da.toString());
                }
            }
            datingPartnerSurveyDto.setAreas(areaNames);
            return new DatingMatchResultDto(7004, SurveyErrorCode.SHOW_MATCH_RESULT.getMessage(), datingPartnerSurveyDto, null);
        }
        // 매칭 실패했을 떄
        else {
            return new DatingMatchResultDto(7005, SurveyErrorCode.MATCH_FAIL.getMessage(), null, null);
        }
    }

    @Transactional
    public List<TestDatingMatchingResultDto> testMatch() {
        List<DatingSurveyEntity> datingSurveyList = datingSurveyRepository.findAllByMatchStatus(MatchStatus.WAITING)
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_WAITING_SURVEY));

        Map<Long, DatingSurveyEntity> datingSurveyMap = new HashMap<>();
        for (DatingSurveyEntity ds : datingSurveyList) {
            datingSurveyMap.put(ds.getId(), ds);
        }

        List<DatingSurveyDto> datingSurveyDtoList = new ArrayList<>();
        for (DatingSurveyEntity ds : datingSurveyList) {
            DatingSurveyDto datingSurveyDto = datingSurveyMapper.toDto(ds);
            datingSurveyDto.setId(ds.getId());
            datingSurveyDtoList.add(datingSurveyDto);
        }

        DatingMatchingAlgorithm datingMatchingAlgorithm = new DatingMatchingAlgorithm();
        List<MatchingInfo<DatingSurveyDto>> result = datingMatchingAlgorithm.getResult(datingSurveyDtoList);

        for (MatchingInfo mi : result) {
            DatingSurveyDto firstDto = (DatingSurveyDto) mi.getFirst();
            DatingSurveyDto secondDto = (DatingSurveyDto) mi.getSecond();

            DatingSurveyEntity firstEntity = datingSurveyMap.get(firstDto.getId());
            DatingSurveyEntity secondEntity = datingSurveyMap.get(secondDto.getId());

            DatingMatchingEntity datingMatching = mi.toDatingMatchingEntity(firstEntity, secondEntity);
            firstEntity.changeDatingMatching(datingMatching);
            secondEntity.changeDatingMatching(datingMatching);

            datingMatching.getMaleSurvey().changeMatchStatus(MatchStatus.MATCHED);
            datingMatching.getFemaleSurvey().changeMatchStatus(MatchStatus.PAID);

            String emailA = datingMatching.getMaleSurvey().getUser().getUniversityEmail();
            String emailB = datingMatching.getFemaleSurvey().getUser().getUniversityEmail();

//            emailService.sendMatchResult(emailA);
//            emailService.sendMatchResult(emailB);

            datingMatchingRepository.save(datingMatching);
        }

        datingSurveyList.forEach((datingSurvey -> {
            if (datingSurvey.getMatchStatus().equals(MatchStatus.WAITING)) {
                datingSurvey.changeMatchStatus(MatchStatus.FAILED);
            }
        }));

        return datingMatchingRepository.findAll()
                .stream().map((matchingEntity) -> TestDatingMatchingResultDto.builder()
                        .matchId(matchingEntity.getId())
                        .maleSurveyId(matchingEntity.getMaleSurvey().getId())
                        .femaleSurveyId(matchingEntity.getFemaleSurvey().getId())
                        .build()).collect(Collectors.toList());
    }
}
