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
import com.yapp.lonessum.domain.dating.scheduler.DatingMatchingScheduler;
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

    private final DatingMatchingScheduler datingMatchingScheduler;

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
            return new DatingMatchResultDto(7000, SurveyErrorCode.NO_EXISTING_SURVEY.getMessage(), null, null, null);
        }

        // 작성한 설문이 있을 때
        // 매칭 대기중일 떄
        if (datingSurvey.getMatchStatus() == MatchStatus.WAITING) {
            return new DatingMatchResultDto(7001, SurveyErrorCode.WAITING_FOR_MATCH.getMessage(), null, null, null);
        }
        // 매칭 성공했을 때
        else if (datingSurvey.getMatchStatus() == MatchStatus.MATCHED) {
            // 내가 남자일 때만 해당
            // 내가 결제 안했을 때
            if (!datingSurvey.getDatingMatching().getPayment().getIsPaid()) {
                return new DatingMatchResultDto(7002, SurveyErrorCode.PAY_FOR_MATCH.getMessage(), null, datingSurvey.getDatingMatching().getMatchedTime().plusDays(1L), datingSurvey.getDatingMatching().getPayment().getPayName());
            }
        }
        else if (datingSurvey.getMatchStatus() == MatchStatus.PAID) {
            // 내가 여자일 때
            if (datingSurvey.getGender() == Gender.FEMALE) {
                // 상대가 결제 안했을 때
                if (!datingSurvey.getDatingMatching().getPayment().getIsPaid()) {
                    return new DatingMatchResultDto(7003, SurveyErrorCode.WAITING_FOR_PAY.getMessage(), null, datingSurvey.getDatingMatching().getMatchedTime().plusDays(1L), null);
                }
            }

            // 모두 결제했을 때 -> 매칭 상대 정보
            if (datingSurvey.getGender() == Gender.MALE) {
                partnerSurvey = datingSurvey.getDatingMatching().getFemaleSurvey();
            }
            else {
                partnerSurvey = datingSurvey.getDatingMatching().getMaleSurvey();
            }

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
            return new DatingMatchResultDto(7004, SurveyErrorCode.SHOW_MATCH_RESULT.getMessage(), datingPartnerSurveyDto, null, null);
        }
        // 매칭 실패했을 떄
        else if (datingSurvey.getMatchStatus() == MatchStatus.FAILED) {
            return new DatingMatchResultDto(7005, SurveyErrorCode.MATCH_FAIL.getMessage(), null, null, null);
        }
        // 환불이 필요한 경우
        return new DatingMatchResultDto(7006, SurveyErrorCode.NEED_REFUND.getMessage(), null, null, null);
    }

    @Transactional
    public List<TestDatingMatchingResultDto> testMatch() {
        datingMatchingScheduler.runMatch();

        return datingMatchingRepository.findAll()
                .stream().map((matchingEntity) -> TestDatingMatchingResultDto.builder()
                        .matchId(matchingEntity.getId())
                        .maleSurveyId(matchingEntity.getMaleSurvey().getId())
                        .femaleSurveyId(matchingEntity.getFemaleSurvey().getId())
                        .build()).collect(Collectors.toList());
    }
}
