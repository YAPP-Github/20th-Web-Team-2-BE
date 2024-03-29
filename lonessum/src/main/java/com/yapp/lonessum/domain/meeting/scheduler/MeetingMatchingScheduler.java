package com.yapp.lonessum.domain.meeting.scheduler;

import com.yapp.lonessum.common.algorithm.MatchingInfo;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.algorithm.MeetingMatchingAlgorithm;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.email.service.EmailService;
import com.yapp.lonessum.domain.payment.entity.MatchType;
import com.yapp.lonessum.domain.payment.entity.PaymentEntity;
import com.yapp.lonessum.domain.payment.repository.PayNameCountRepository;
import com.yapp.lonessum.domain.payment.repository.PaymentRepository;
import com.yapp.lonessum.domain.payment.service.PayNameService;
import com.yapp.lonessum.domain.payment.service.PaymentService;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.mapper.MeetingSurveyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MeetingMatchingScheduler {

    private final EmailService emailService;
    private final MeetingSurveyMapper meetingSurveyMapper;
    private final MeetingSurveyRepository meetingSurveyRepository;
    private final MeetingMatchingRepository meetingMatchingRepository;
    private final PayNameService payNameService;
    private final PaymentRepository paymentRepository;
    private final PayNameCountRepository payNameCountRepository;

    @Transactional
    @Scheduled(cron = "02 00 22 * * ?")
    public void runMatch() {

        log.info("Start Today's Meeting Matching");

        payNameCountRepository.setPayNameCounter(0);

        List<MeetingSurveyEntity> meetingSurveyList = meetingSurveyRepository.findAllByMatchStatus(MatchStatus.WAITING)
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_WAITING_SURVEY));

        Map<Long, MeetingSurveyEntity> meetingSurveyMap = new HashMap<>();
        for (MeetingSurveyEntity ms : meetingSurveyList) {
            meetingSurveyMap.put(ms.getId(), ms);
        }

        List<MeetingSurveyDto> meetingSurveyDtoList = new ArrayList<>();
        for (MeetingSurveyEntity ms : meetingSurveyList) {
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

            meetingMatching.getMaleSurvey().changeMatchStatus(MatchStatus.MATCHED);
            meetingMatching.getFemaleSurvey().changeMatchStatus(MatchStatus.PAID);

            String emailA = meetingMatching.getMaleSurvey().getUser().getUniversityEmail();
            String emailB = meetingMatching.getFemaleSurvey().getUser().getUniversityEmail();

            try {
                log.info("send match result to"+emailA);
                log.info("send match result to"+emailB);
                emailService.sendMatchResult(emailA);
                emailService.sendMatchResult(emailB);
            } catch (MessagingException e) {
                log.warn("매칭 결과 이메일 전송 실패", emailA);
                log.warn("매칭 결과 이메일 전송 실패", emailB);
                throw new RestApiException(UserErrorCode.FAIL_TO_SEND_EMAIL);
            }

            PaymentEntity payment = PaymentEntity.builder()
                    .payName(payNameService.getPayName())
                    .matchType(MatchType.MEETING)
                    .meetingMatching(meetingMatching)
                    .isPaid(false)
                    .isNeedRefund(false)
                    .build();
            paymentRepository.save(payment);

            meetingMatching.changePayment(payment);

            meetingMatchingRepository.save(meetingMatching);
        }

        meetingSurveyList.forEach((meetingSurvey -> {
            if (meetingSurvey.getMatchStatus().equals(MatchStatus.WAITING)) {
                meetingSurvey.changeMatchStatus(MatchStatus.FAILED);
                String email = meetingSurvey.getUser().getUniversityEmail();
                try {
                    log.info("send match result(failed) to"+email);
                    emailService.sendMatchResult(email);
                } catch (MessagingException e) {
                    log.warn("매칭 결과 이메일 전송 실패", email);
                    throw new RestApiException(UserErrorCode.FAIL_TO_SEND_EMAIL);
                }
            }
        }));

        log.info("Finished Today's Meeting Matching");
    }

    // 결제 마감 시간까지 결제하지 않은 유저는 MatchStatus가 MATCHED -> FAILED로 변경
    @Transactional
    @Scheduled(cron = "00 00 22 * * ?")
    public void matchedToFailed() {
        List<MeetingSurveyEntity> matchedSurveyList = meetingSurveyRepository.findAllByMatchStatus(MatchStatus.MATCHED)
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_MATCHED_SURVEY));
        matchedSurveyList.forEach((matchedSurvey) -> {
            // 남자 설문
            matchedSurvey.changeMatchStatus(MatchStatus.FAILED);
            // 그와 매칭된 여자 설문
            matchedSurvey.getMeetingMatching().getFemaleSurvey().changeMatchStatus(MatchStatus.FAILED);
        });
    }
}
