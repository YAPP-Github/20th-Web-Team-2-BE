package com.yapp.lonessum.domain.admin;

import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.dating.repository.DatingMatchingRepository;
import com.yapp.lonessum.domain.dating.repository.DatingSurveyRepository;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.payment.entity.MatchType;
import com.yapp.lonessum.domain.payment.entity.PaymentEntity;
import com.yapp.lonessum.domain.payment.repository.PaymentRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {
    @Autowired
    AdminService adminService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MeetingSurveyRepository meetingSurveyRepository;

    @Autowired
    DatingSurveyRepository datingSurveyRepository;

    @Autowired
    MeetingMatchingRepository meetingMatchingRepository;

    @Autowired
    DatingMatchingRepository datingMatchingRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    @Transactional
    void setUserMeetingPayment() {
        UserEntity male = UserEntity.builder()
                .kakaoServerId(1L)
                .build();
        UserEntity female = UserEntity.builder()
                .kakaoServerId(2L)
                .build();

        userRepository.save(male);
        userRepository.save(female);

        MeetingSurveyEntity maleSurvey = MeetingSurveyEntity.builder()
                .user(male)
                .gender(Gender.MALE)
                .matchStatus(MatchStatus.MATCHED)
                .kakaoId("abc")
                .build();
        MeetingSurveyEntity femaleSurvey = MeetingSurveyEntity.builder()
                .user(female)
                .gender(Gender.FEMALE)
                .matchStatus(MatchStatus.PAID)
                .kakaoId("cde")
                .build();

        male.changeMeetingSurvey(maleSurvey);
        male.changeMeetingSurvey(femaleSurvey);

        meetingSurveyRepository.save(maleSurvey);
        meetingSurveyRepository.save(femaleSurvey);

        PaymentEntity payment = PaymentEntity.builder()
                .isPaid(false)
                .matchType(MatchType.MEETING)
                .build();

        paymentRepository.save(payment);

        MeetingMatchingEntity meetingMatching = MeetingMatchingEntity.builder()
                .maleSurvey(maleSurvey)
                .femaleSurvey(femaleSurvey)
                .payment(payment)
                .build();

        payment.changeMeetingMatching(meetingMatching);

        maleSurvey.changeMeetingMatching(meetingMatching);
        femaleSurvey.changeMeetingMatching(meetingMatching);

        meetingMatchingRepository.save(meetingMatching);

        adminService.setUserMeetingPayment(new PaymentDto(maleSurvey.getKakaoId()));
        Assertions.assertEquals(payment.getIsPaid(), true);
    }

    @Test
    @Transactional
    void setUserDatingPayment() {
        UserEntity male = UserEntity.builder()
                .kakaoServerId(1L)
                .build();
        UserEntity female = UserEntity.builder()
                .kakaoServerId(2L)
                .build();

        userRepository.save(male);
        userRepository.save(female);

        DatingSurveyEntity maleSurvey = DatingSurveyEntity.builder()
                .user(male)
                .gender(Gender.MALE)
                .matchStatus(MatchStatus.MATCHED)
                .kakaoId("abc")
                .build();
        DatingSurveyEntity femaleSurvey = DatingSurveyEntity.builder()
                .user(female)
                .gender(Gender.FEMALE)
                .matchStatus(MatchStatus.PAID)
                .kakaoId("cde")
                .build();

        male.changeDatingSurvey(maleSurvey);
        male.changeDatingSurvey(femaleSurvey);

        datingSurveyRepository.save(maleSurvey);
        datingSurveyRepository.save(femaleSurvey);

        PaymentEntity payment = PaymentEntity.builder()
                .isPaid(false)
                .matchType(MatchType.DATING)
                .build();

        paymentRepository.save(payment);

        DatingMatchingEntity datingMatching = DatingMatchingEntity.builder()
                .maleSurvey(maleSurvey)
                .femaleSurvey(femaleSurvey)
                .payment(payment)
                .build();

        payment.changeDatingMatching(datingMatching);

        maleSurvey.changeDatingMatching(datingMatching);
        femaleSurvey.changeDatingMatching(datingMatching);

        datingMatchingRepository.save(datingMatching);

        adminService.setUserDatingPayment(new PaymentDto(maleSurvey.getKakaoId()));
        Assertions.assertEquals(payment.getIsPaid(), true);
    }
}