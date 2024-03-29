package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.meeting.service.MeetingSurveyService;
import com.yapp.lonessum.domain.payment.entity.MatchType;
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
    @Autowired
    MeetingSurveyService meetingSurveyService;

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

        MeetingMatchingEntity meetingMatching = MeetingMatchingEntity.builder()
                .maleSurvey(maleSurvey)
                .femaleSurvey(femaleSurvey)
                .build();

        //결제 정보
        PaymentEntity payment = PaymentEntity
                .builder()
                .meetingMatching(meetingMatching)
                .isPaid(false)
                .isNeedRefund(false).build();
        payment = paymentRepository.save(payment);

        meetingMatching.changePayment(payment);

        maleSurvey.changeMeetingMatching(meetingMatching);
        femaleSurvey.changeMeetingMatching(meetingMatching);

        meetingMatchingRepository.save(meetingMatching);

        payment.payForMatching(MatchType.MEETING);

        //when
        userService.withdraw(female.getId());

        //then
        PaymentEntity result = paymentRepository.findById(payment.getId()).get();
        Assertions.assertThat(result.getIsNeedRefund())
                .isEqualTo(true);
        Assertions.assertThat(meetingMatching.getMaleSurvey().getMatchStatus())
                .isEqualTo(MatchStatus.CANCELED_OR_NEED_REFUND);
    }

    @Test
    @Transactional
    public void 남자_탈퇴_시_해당_매칭은_환불이_필요없다() {
        //given
        //남자 설문
        MeetingSurveyEntity maleSurvey = MeetingSurveyEntity.builder().gender(Gender.MALE).matchStatus(MatchStatus.WAITING).build();
        maleSurvey = meetingSurveyRepository.save(maleSurvey);

        //남자 유저
        UserEntity male = UserEntity.builder().meetingSurvey(maleSurvey).build();

        //여자 설문
        MeetingSurveyEntity femaleSurvey = MeetingSurveyEntity.builder().gender(Gender.FEMALE).matchStatus(MatchStatus.WAITING).build();
        femaleSurvey = meetingSurveyRepository.save(femaleSurvey);

        //여자 유저
        UserEntity female = UserEntity.builder().meetingSurvey(femaleSurvey).build();

        male = userRepository.save(male);
        female = userRepository.save(female);

        MeetingMatchingEntity meetingMatching = MeetingMatchingEntity.builder()
                .maleSurvey(maleSurvey)
                .femaleSurvey(femaleSurvey)
                .build();

        maleSurvey.changeMeetingMatching(meetingMatching);
        femaleSurvey.changeMeetingMatching(meetingMatching);

        //결제 정보
        PaymentEntity payment = PaymentEntity
                .builder()
                .meetingMatching(meetingMatching)
                .isPaid(false)
                .isNeedRefund(false).build();
        payment = paymentRepository.save(payment);

        meetingMatching.changePayment(payment);

        maleSurvey.changeMatchStatus(MatchStatus.MATCHED);
        femaleSurvey.changeMatchStatus(MatchStatus.PAID);

        meetingMatchingRepository.save(meetingMatching);

        payment.payForMatching(MatchType.MEETING);

        //when
        userService.withdraw(male.getId());

        //then
        Assertions.assertThat(payment.getIsNeedRefund())
                .isEqualTo(false);
        Assertions.assertThat(meetingMatching.getFemaleSurvey().getMatchStatus())
                .isEqualTo(MatchStatus.PAID);
    }

    @Test
    @Transactional
    public void 다시_매칭하기() {
        //given
        //남자 설문
        MeetingSurveyEntity maleSurvey = MeetingSurveyEntity.builder().gender(Gender.MALE).matchStatus(MatchStatus.WAITING).build();
        maleSurvey = meetingSurveyRepository.save(maleSurvey);

        //남자 유저
        UserEntity male = UserEntity.builder().meetingSurvey(maleSurvey).build();

        //여자 설문
        MeetingSurveyEntity femaleSurvey = MeetingSurveyEntity.builder().gender(Gender.FEMALE).matchStatus(MatchStatus.WAITING).build();
        femaleSurvey = meetingSurveyRepository.save(femaleSurvey);

        //여자 유저
        UserEntity female = UserEntity.builder().meetingSurvey(femaleSurvey).build();

        male = userRepository.save(male);
        female = userRepository.save(female);

        MeetingMatchingEntity meetingMatching = MeetingMatchingEntity.builder()
                .maleSurvey(maleSurvey)
                .femaleSurvey(femaleSurvey)
                .build();

        maleSurvey.changeMeetingMatching(meetingMatching);
        femaleSurvey.changeMeetingMatching(meetingMatching);

        //결제 정보
        PaymentEntity payment = PaymentEntity
                .builder()
                .meetingMatching(meetingMatching)
                .isPaid(false)
                .isNeedRefund(false).build();
        payment = paymentRepository.save(payment);

        meetingMatching.changePayment(payment);

        maleSurvey.changeMatchStatus(MatchStatus.MATCHED);
        femaleSurvey.changeMatchStatus(MatchStatus.PAID);

        meetingMatchingRepository.save(meetingMatching);

        payment.payForMatching(MatchType.MEETING);

        meetingSurveyService.rematchSurvey(male);

        entityManager.flush();

        entityManager.clear();

        Optional<MeetingSurveyEntity> ms = meetingSurveyRepository.findById(maleSurvey.getId());
        Assertions.assertThat(ms.get().getMeetingMatching()).isNotNull();

        Optional<MeetingMatchingEntity> mm = meetingMatchingRepository.findById(meetingMatching.getId());
        Assertions.assertThat(mm.get().getMaleSurvey()).isNotNull();

        Assertions.assertThat(mm.get().getMaleSurvey().getMeetingMatching()).isNotNull();

        Assertions.assertThat(mm.get().getFemaleSurvey().getMatchStatus()).isEqualTo(MatchStatus.PAID);
    }
}
