package com.yapp.lonessum.domain.meeting.scheduler;

import com.yapp.lonessum.common.algorithm.MatchingInfo;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.algorithm.MeetingMatchingAlgorithm;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.user.service.EmailService;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.mapper.MeetingSurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MeetingMatchingScheduler {

    private final MeetingSurveyMapper meetingSurveyMapper;
    private final MeetingSurveyRepository meetingSurveyRepository;
    private final MeetingMatchingRepository meetingMatchingRepository;
    private final EmailService emailService;

    @Transactional
    @Scheduled(cron = "00 00 22 * * ?")
    public void runMatch() {
        List<MeetingSurveyEntity> meetingSurveyList = meetingSurveyRepository.findAllByMatchStatus(MatchStatus.WAITING)
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_WAITING_SURVEY));

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
            MeetingMatchingEntity meetingMatching = mi.toMeetingMatchingEntity();
            String emailA = meetingMatching.getMaleSurvey().getUser().getUniversityEmail();
            String emailB = meetingMatching.getFemaleSurvey().getUser().getUniversityEmail();
            emailService.sendMatchResult(emailA);
            emailService.sendMatchResult(emailB);
        }
        result.forEach(matchingInfo -> meetingMatchingRepository.save(matchingInfo.toMeetingMatchingEntity()));
    }
}
