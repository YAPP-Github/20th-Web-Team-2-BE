package com.yapp.lonessum.domain.meeting.repository;

import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.payment.entity.PaymentEntity;
import com.yapp.lonessum.domain.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MeetingMatchingRepositoryTest {
    @Autowired
    MeetingMatchingRepository meetingMatchingRepository;
    @Autowired
    MeetingSurveyRepository meetingSurveyRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @Test
    @Transactional
    public void 여자_설문_기반으로_조회() {
        //given
        MeetingSurveyEntity surveyEntity = meetingSurveyRepository.save(
                MeetingSurveyEntity
                        .builder()
                        .build());

        PaymentEntity payment = paymentRepository.save(PaymentEntity.builder().build());

        meetingMatchingRepository.save(MeetingMatchingEntity.builder()
                .femaleSurvey(surveyEntity)
                .payment(payment)
                .build());

        //when
        MeetingMatchingEntity meetingMatchingEntity =
                meetingMatchingRepository.findWithFeMaleSurvey(surveyEntity.getId()).get();

        //then
        Assertions.assertEquals(surveyEntity.getId(), meetingMatchingEntity.getFemaleSurvey().getId());
    }
}
