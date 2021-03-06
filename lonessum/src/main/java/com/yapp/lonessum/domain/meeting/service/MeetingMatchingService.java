package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.common.algorithm.MatchingInfo;
import com.yapp.lonessum.domain.constant.DomesticArea;
import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.algorithm.MeetingMatchingAlgorithm;
import com.yapp.lonessum.domain.meeting.dto.MeetingMatchResultDto;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.dto.MeetingPartnerSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.service.AbroadAreaService;
import com.yapp.lonessum.domain.user.service.UniversityService;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.mapper.MeetingSurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MeetingMatchingService {

    private final UniversityService universityService;
    private final AbroadAreaService abroadAreaService;
    private final MeetingSurveyRepository meetingSurveyRepository;

    private final MeetingSurveyMapper meetingSurveyMapper;
    private final MeetingMatchingRepository meetingMatchingRepository;

    public MeetingMatchResultDto getMatchResult(UserEntity user) {

        MeetingSurveyEntity meetingSurvey = user.getMeetingSurvey();
        MeetingSurveyEntity partnerSurvey;

        if (meetingSurvey == null) {
            // ????????? ????????? ?????? ???
            return new MeetingMatchResultDto(7000, SurveyErrorCode.NO_EXISTING_SURVEY.getMessage(), null);
        }

        // ????????? ????????? ?????? ???
        // ?????? ?????? ????????? ??? -> ?????? ?????? ?????? ??????
        if (meetingSurvey.getMatchStatus() == MatchStatus.DONE) {
            return new MeetingMatchResultDto(7001, SurveyErrorCode.NO_WAITING_SURVEY.getMessage(), null);
        }
        // ?????? ???????????? ???
        else if (meetingSurvey.getMatchStatus() == MatchStatus.WAITING) {
            return new MeetingMatchResultDto(7002, SurveyErrorCode.WAITING_FOR_MATCH.getMessage(), null);
        }
        // ?????? ???????????? ???
        else if (meetingSurvey.getMatchStatus() == MatchStatus.MATCHED) {
            // ?????? ????????? ???
            if (meetingSurvey.getGender() == Gender.MALE) {
                partnerSurvey = meetingSurvey.getMeetingMatching().getFemaleSurvey();
                // ?????? ?????? ????????? ???
                if (meetingSurvey.getPayment() == null) {
                    return new MeetingMatchResultDto(7003, SurveyErrorCode.PAY_FOR_MATCH.getMessage(), null);
                }
            }
            // ?????? ????????? ???
            else {
                partnerSurvey = meetingSurvey.getMeetingMatching().getMaleSurvey();
                // ????????? ?????? ????????? ???
                if (partnerSurvey.getPayment() == null) {
                    return new MeetingMatchResultDto(7004, SurveyErrorCode.WAITING_FOR_PAY.getMessage(), null);
                }
            }
            // ?????? ???????????? ??? -> ?????? ?????? ??????
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
            return new MeetingMatchResultDto(7005, SurveyErrorCode.SHOW_MATCH_RESULT.getMessage(), meetingPartnerSurveyDto);
        }
        // ?????? ???????????? ???
        else {
            return new MeetingMatchResultDto(7006, SurveyErrorCode.MATCH_FAIL.getMessage(), null);
        }
    }

    @Transactional
    public List<MeetingMatchingEntity> testMatch() {
        List<MeetingSurveyEntity> meetingSurveyList = meetingSurveyRepository.findAllByMatchStatus(MatchStatus.WAITING)
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_WAITING_SURVEY));

        Map<Long, MeetingSurveyEntity> meetingSurveyMap = new HashMap<>();
        for (MeetingSurveyEntity ms : meetingSurveyList) {
            meetingSurveyMap.put(ms.getId(), ms);
        }

        List<MeetingSurveyDto> meetingSurveyDtoList = new ArrayList<>();
        for (MeetingSurveyEntity ms : meetingSurveyList) {
            ms.changeMatchStatus(MatchStatus.MATCHED);
            MeetingSurveyDto meetingSurveyDto = meetingSurveyMapper.toDto(ms);
            meetingSurveyDto.setId(ms.getId());
            meetingSurveyDtoList.add(meetingSurveyDto);
        }

        MeetingMatchingAlgorithm meetingMatchingAlgorithm = new MeetingMatchingAlgorithm();
        List<MatchingInfo<MeetingSurveyDto>> result = meetingMatchingAlgorithm.getResult(meetingSurveyDtoList);
        for (MatchingInfo mi : result) {
            MeetingSurveyDto firstDto = (MeetingSurveyDto) mi.getFirst();
            MeetingSurveyDto secondDto = (MeetingSurveyDto) mi.getSecond();

            MeetingSurveyEntity firstEntity = meetingSurveyMap.get(firstDto.getId());
            MeetingSurveyEntity secondEntity = meetingSurveyMap.get(secondDto.getId());

            MeetingMatchingEntity meetingMatching = mi.toMeetingMatchingEntity(firstEntity, secondEntity);
            firstEntity.changeMeetingMatching(meetingMatching);
            secondEntity.changeMeetingMatching(meetingMatching);

            String emailA = meetingMatching.getMaleSurvey().getUser().getUniversityEmail();
            String emailB = meetingMatching.getFemaleSurvey().getUser().getUniversityEmail();

//            emailService.sendMatchResult(emailA);
//            emailService.sendMatchResult(emailB);

            meetingMatchingRepository.save(meetingMatching);
        }
        return meetingMatchingRepository.findAll();
    }
}
