package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.payment.entity.PaymentEntity;
import com.yapp.lonessum.domain.payment.repository.PaymentRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MeetingMatchingRepository meetingMatchingRepository;
    @Autowired
    MeetingSurveyRepository meetingSurveyRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    public void 회원탈퇴_테스트() {
        //given
        UserEntity user = userRepository.save(UserEntity.builder().build());

        //when
        userService.withdraw(user.getId());

        //then
        Assertions.assertThat(userRepository.findById(1L).isEmpty()).isEqualTo(true);
    }

    @Test
    @Transactional
    public void 여자_탈퇴_시_해당_매칭은_환불이_필요하다() {
        //given
        //남자 설문
        MeetingSurveyEntity maleSurvey = MeetingSurveyEntity.builder().gender(Gender.MALE).matchStatus(MatchStatus.MATCHED).build();
        maleSurvey = meetingSurveyRepository.save(maleSurvey);

        //남자 유저
        UserEntity male = UserEntity.builder().meetingSurvey(maleSurvey).build();

        //여자 설문
        MeetingSurveyEntity femaleSurvey = MeetingSurveyEntity.builder().gender(Gender.FEMALE).matchStatus(MatchStatus.PAID).build();
        femaleSurvey = meetingSurveyRepository.save(femaleSurvey);

        //여자 유저
        UserEntity female = UserEntity.builder().meetingSurvey(femaleSurvey).build();

        male = userRepository.save(male);
        female = userRepository.save(female);

        //결제 정보
        PaymentEntity payment = PaymentEntity
                .builder()
                .isNeedRefund(false).build();
        payment = paymentRepository.save(payment);

        MeetingMatchingEntity meetingMatching = MeetingMatchingEntity.builder()
                .maleSurvey(maleSurvey)
                .femaleSurvey(femaleSurvey)
                .payment(payment)
                .build();

        maleSurvey.changeMeetingMatching(meetingMatching);
        femaleSurvey.changeMeetingMatching(meetingMatching);

        meetingMatchingRepository.save(meetingMatching);

        //when
        userService.withdraw(female.getId());

        //then
        PaymentEntity result = paymentRepository.findById(payment.getId()).get();
        Assertions.assertThat(result.getIsNeedRefund())
                .isEqualTo(true);
        Assertions.assertThat(meetingMatching.getMaleSurvey().getMatchStatus())
                .isEqualTo(MatchStatus.NEED_REFUND);
    }

    @Test
    @Transactional
    public void 남자_탈퇴_시_해당_매칭은_환불이_필요없다() {
        //given
        //남자 설문
        MeetingSurveyEntity maleSurvey = MeetingSurveyEntity.builder().gender(Gender.MALE).build();
        maleSurvey = meetingSurveyRepository.save(maleSurvey);

        //남자 유저
        UserEntity male = UserEntity.builder().meetingSurvey(maleSurvey).build();

        //여자 설문
        MeetingSurveyEntity femaleSurvey = MeetingSurveyEntity.builder().gender(Gender.FEMALE).build();
        femaleSurvey = meetingSurveyRepository.save(femaleSurvey);

        //여자 유저
        UserEntity female = UserEntity.builder().meetingSurvey(femaleSurvey).build();

        male = userRepository.save(male);
        female = userRepository.save(female);

        //결제 정보
        PaymentEntity payment = PaymentEntity
                .builder()
                .isNeedRefund(false).build();
        payment = paymentRepository.save(payment);

        MeetingMatchingEntity meetingMatching = MeetingMatchingEntity.builder()
                .maleSurvey(maleSurvey)
                .femaleSurvey(femaleSurvey)
                .payment(payment)
                .build();

        maleSurvey.changeMeetingMatching(meetingMatching);
        femaleSurvey.changeMeetingMatching(meetingMatching);

        meetingMatchingRepository.save(meetingMatching);

        //when
        userService.withdraw(male.getId());

        //then
        PaymentEntity result = paymentRepository.findById(payment.getId()).get();
        Assertions.assertThat(result.getIsNeedRefund())
                .isEqualTo(false);
        Assertions.assertThat(meetingMatching.getFemaleSurvey().getMatchStatus())
                .isEqualTo(MatchStatus.FAILED);
    }
}
