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
import com.yapp.lonessum.domain.payment.entity.Payment;
import com.yapp.lonessum.domain.payment.repository.PaymentRepository;
import com.yapp.lonessum.domain.payment.service.PaymentService;
import com.yapp.lonessum.exception.errorcode.SurveyErrorCode;
import com.yapp.lonessum.exception.exception.RestApiException;
import com.yapp.lonessum.mapper.DatingSurveyMapper;
import lombok.RequiredArgsConstructor;
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
public class DatingMatchingScheduler {

    private final EmailService emailService;
    private final DatingSurveyMapper datingSurveyMapper;
    private final DatingSurveyRepository datingSurveyRepository;
    private final DatingMatchingRepository datingMatchingRepository;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @Transactional
    @Scheduled(cron = "00 00 22 * * ?")
    public void runMatch() throws MessagingException {
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

            emailService.sendMatchResult(emailA);
            emailService.sendMatchResult(emailB);

            paymentRepository.save(Payment.builder()
                    .payName(paymentService.generatePayName())
                    .matchType(MatchType.MEETING)
                    .datingMatching(datingMatching)
                    .isPaid(false)
                    .build());

            datingMatchingRepository.save(datingMatching);
        }

        datingSurveyList.forEach((datingSurvey -> {
            if (datingSurvey.getMatchStatus().equals(MatchStatus.WAITING)) {
                datingSurvey.changeMatchStatus(MatchStatus.FAILED);
            }
        }));
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
