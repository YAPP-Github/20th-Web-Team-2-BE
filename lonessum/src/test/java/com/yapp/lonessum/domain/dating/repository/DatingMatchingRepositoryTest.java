package com.yapp.lonessum.domain.dating.repository;

import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.payment.entity.PaymentEntity;
import com.yapp.lonessum.domain.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class DatingMatchingRepositoryTest {
    @Autowired
    DatingMatchingRepository datingMatchingRepository;
    @Autowired
    DatingSurveyRepository datingSurveyRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @Test
    @Transactional
    public void 여자_설문_기반으로_조회() {
        //given
        DatingSurveyEntity surveyEntity = datingSurveyRepository.save(
                DatingSurveyEntity
                        .builder()
                        .build());

        PaymentEntity payment = paymentRepository.save(PaymentEntity.builder().build());

        datingMatchingRepository.save(DatingMatchingEntity.builder()
                .femaleSurvey(surveyEntity)
                .payment(payment)
                .build());


        //when
        DatingMatchingEntity datingMatchingEntity =
                datingMatchingRepository.findWithFeMaleSurvey(surveyEntity.getId()).get();

        //then
        Assertions.assertEquals(surveyEntity.getId(), datingMatchingEntity.getFemaleSurvey().getId());
    }
}
