package com.yapp.lonessum.domain.dating.scheduler;

import com.yapp.lonessum.common.algorithm.MatchingInfo;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.dating.algorithm.DatingMatchingAlgorithm;
import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;
import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.dating.repository.DatingMatchingRepository;
import com.yapp.lonessum.domain.dating.repository.DatingSurveyRepository;
import com.yapp.lonessum.domain.email.service.EmailService;
import com.yapp.lonessum.domain.payment.entity.MatchType;
import com.yapp.lonessum.domain.payment.entity.PaymentEntity;
import com.yapp.lonessum.domain.payment.repository.PaymentRepository;
import com.yapp.lonessum.domain.payment.service.PayNameService;
import com.yapp.lonessum.domain.payment.service.PaymentService;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.errorcode.UserErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.mapper.DatingSurveyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatingMatchingScheduler {

    private final EmailService emailService;
    private final DatingSurveyMapper datingSurveyMapper;
    private final DatingSurveyRepository datingSurveyRepository;
    private final DatingMatchingRepository datingMatchingRepository;
    private final PayNameService payNameService;
    private final PaymentRepository paymentRepository;

    @Transactional
    @Scheduled(cron = "02 00 22 * * ?")
    public void runMatch() {

        log.info("Start Today's Dating Matching");

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
                    .matchType(MatchType.DATING)
                    .datingMatching(datingMatching)
                    .isPaid(false)
                    .isNeedRefund(false)
                    .build();
            paymentRepository.save(payment);

            datingMatching.changePayment(payment);

            datingMatchingRepository.save(datingMatching);
        }

        datingSurveyList.forEach((datingSurvey -> {
            if (datingSurvey.getMatchStatus().equals(MatchStatus.WAITING)) {
                datingSurvey.changeMatchStatus(MatchStatus.FAILED);
                String email = datingSurvey.getUser().getUniversityEmail();
                try {
                    log.info("send match result(failed) to"+email);
                    emailService.sendMatchResult(email);
                } catch (MessagingException e) {
                    log.warn("매칭 결과 이메일 전송 실패", email);
                    throw new RestApiException(UserErrorCode.FAIL_TO_SEND_EMAIL);
                }
            }
        }));

        log.info("Finished Today's Dating Matching");
    }

    // 결제 마감 시간까지 결제하지 않은 유저는 MatchStatus가 MATCHED -> FAILED로 변경
    @Transactional
    @Scheduled(cron = "00 00 22 * * ?")
    public void matchedToFailed() {
        List<DatingSurveyEntity> matchedSurveyList = datingSurveyRepository.findAllByMatchStatus(MatchStatus.MATCHED)
                .orElseThrow(() -> new RestApiException(SurveyErrorCode.NO_MATCHED_SURVEY));
        matchedSurveyList.forEach((matchedSurvey) -> {
            // 남자 설문
            matchedSurvey.changeMatchStatus(MatchStatus.FAILED);
            // 그와 매칭된 여자 설문
            matchedSurvey.getDatingMatching().getFemaleSurvey().changeMatchStatus(MatchStatus.FAILED);
        });
    }
}
