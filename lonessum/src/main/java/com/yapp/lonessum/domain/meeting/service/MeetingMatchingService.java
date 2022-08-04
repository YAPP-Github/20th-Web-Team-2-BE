package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.common.algorithm.MatchingInfo;
import com.yapp.lonessum.domain.constant.DomesticArea;
import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.email.service.EmailService;
import com.yapp.lonessum.domain.meeting.algorithm.MeetingMatchingAlgorithm;
import com.yapp.lonessum.domain.meeting.dto.MeetingMatchResultDto;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.dto.MeetingPartnerSurveyDto;
import com.yapp.lonessum.domain.meeting.dto.TestMeetingMatchingResultDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.meeting.scheduler.MeetingMatchingScheduler;
import com.yapp.lonessum.domain.payment.entity.MatchType;
import com.yapp.lonessum.domain.payment.entity.PaymentEntity;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.abroadArea.AbroadAreaService;
import com.yapp.lonessum.domain.university.UniversityService;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.mapper.MeetingSurveyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeetingMatchingService {

    private final MeetingMatchingScheduler meetingMatchingScheduler;

    private final UniversityService universityService;
    private final AbroadAreaService abroadAreaService;
    private final MeetingSurveyRepository meetingSurveyRepository;

    private final MeetingSurveyMapper meetingSurveyMapper;
    private final MeetingMatchingRepository meetingMatchingRepository;

    public MeetingMatchResultDto getMatchResult(UserEntity user) {

        MeetingSurveyEntity meetingSurvey = user.getMeetingSurvey();
        MeetingSurveyEntity partnerSurvey;

        if (meetingSurvey == null) {
            // 작성한 설문이 없을 때
            return new MeetingMatchResultDto(7000, SurveyErrorCode.NO_EXISTING_SURVEY.getMessage(), null, null, null);
        }

        // 작성한 설문이 있을 때
        // 매칭 대기중일 떄
        if (meetingSurvey.getMatchStatus() == MatchStatus.WAITING) {
            return new MeetingMatchResultDto(7001, SurveyErrorCode.WAITING_FOR_MATCH.getMessage(), null, null, null);
        }
        // 매칭 성공했을 때
        else if (meetingSurvey.getMatchStatus() == MatchStatus.MATCHED) {
            // 내가 남자일 때만 해당
            // 내가 결제 안했을 때
            if (!meetingSurvey.getMeetingMatching().getPayment().getIsPaid()) {
                return new MeetingMatchResultDto(7002, SurveyErrorCode.PAY_FOR_MATCH.getMessage(), null, meetingSurvey.getMeetingMatching().getMatchedTime().plusDays(1L), meetingSurvey.getMeetingMatching().getPayment().getPayName());
            }
        }
        else if (meetingSurvey.getMatchStatus() == MatchStatus.PAID) {
            // 내가 여자일 때
            if (meetingSurvey.getGender() == Gender.FEMALE) {
                // 상대가 결제 안했을 때
                if (!meetingSurvey.getMeetingMatching().getPayment().getIsPaid()) {
                    return new MeetingMatchResultDto(7003, SurveyErrorCode.WAITING_FOR_PAY.getMessage(), null, meetingSurvey.getMeetingMatching().getMatchedTime().plusDays(1L), null);
                }
            }

            // 모두 결제했을 때 -> 매칭 상대 정보
            if (meetingSurvey.getGender() == Gender.MALE) {
                partnerSurvey = meetingSurvey.getMeetingMatching().getFemaleSurvey();
            }
            else {
                partnerSurvey = meetingSurvey.getMeetingMatching().getMaleSurvey();
            }

            MeetingPartnerSurveyDto meetingPartnerSurveyDto = partnerSurvey.toPartnerSurveyDto();

            List<String> universityNames = universityService.getUniversityNameFromId(partnerSurvey.getOurUniversities());
            meetingPartnerSurveyDto.setUniversities(universityNames);

            List<String> areaNames = new ArrayList<>();
            if (meetingSurvey.getIsAbroad()) {
                areaNames = abroadAreaService.getAreaNameFromId(partnerSurvey.getAbroadAreas());
            }
            else {
                List<DomesticArea> domesticAreas = partnerSurvey.getDomesticAreas();
                for (DomesticArea da : domesticAreas) {
                    areaNames.add(da.toString());
                }
            }
            meetingPartnerSurveyDto.setAreas(areaNames);
            return new MeetingMatchResultDto(7004, SurveyErrorCode.SHOW_MATCH_RESULT.getMessage(), meetingPartnerSurveyDto, null, null);
        }
        // 매칭 실패했을 떄
        else if(meetingSurvey.getMatchStatus() == MatchStatus.FAILED) {
            return new MeetingMatchResultDto(7005, SurveyErrorCode.MATCH_FAIL.getMessage(), null, null, null);
        }
        //환불이 필요한 경우
        return new MeetingMatchResultDto(7006, SurveyErrorCode.CANCELED_OR_NEED_REFUND.getMessage(), null, null, null);
    }

    @Transactional
    public List<TestMeetingMatchingResultDto> testMatch() {
        meetingMatchingScheduler.runMatch();
        List<MeetingMatchingEntity> matchingEntityList = meetingMatchingRepository.findAll();
        if (matchingEntityList == null || matchingEntityList.size() == 0) {
            log.info("matching size is 0");
            return new ArrayList<>();
        }
        return matchingEntityList
                .stream().map((matchingEntity) -> TestMeetingMatchingResultDto.builder()
                .matchId(matchingEntity.getId())
                .maleKakaoId(matchingEntity.getMaleSurvey().getKakaoId())
                .femaleKakaoId(matchingEntity.getFemaleSurvey().getKakaoId())
                .build()).collect(Collectors.toList());
    }
}
