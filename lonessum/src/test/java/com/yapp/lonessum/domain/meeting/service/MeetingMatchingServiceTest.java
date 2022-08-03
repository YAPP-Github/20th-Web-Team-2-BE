package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.domain.meeting.dto.MeetingMatchResultDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.meeting.scheduler.MeetingMatchingScheduler;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.yapp.lonessum.domain.meeting.utils.testDataFactory.getTestDataEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MeetingMatchingServiceTest {

    @Autowired
    MeetingMatchingService meetingMatchingService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MeetingMatchingScheduler meetingMatchingScheduler;

    @Autowired
    MeetingSurveyRepository meetingSurveyRepository;

    @Test
    @Transactional
    void getMatchResult() {//given

        UserEntity male = UserEntity.builder().userName("boy").build();
        UserEntity female = UserEntity.builder().userName("girl").build();
        UserEntity female2 = UserEntity.builder().userName("girl2").build();

        userRepository.save(male);
        userRepository.save(female);
        userRepository.save(female2);

        List<MeetingSurveyEntity> surveyList = getTestDataEntity();
        surveyList.forEach((meetingSurvey -> {
            meetingSurveyRepository.save(meetingSurvey);
        }));
        male.changeMeetingSurvey(surveyList.get(0));
        female.changeMeetingSurvey(surveyList.get(1));
        female2.changeMeetingSurvey(surveyList.get(2));

        //when
        meetingMatchingScheduler.runMatch();

        MeetingMatchResultDto matchResult1 = meetingMatchingService.getMatchResult(male);
        MeetingMatchResultDto matchResult2 = meetingMatchingService.getMatchResult(female);
        MeetingMatchResultDto matchResult3 = meetingMatchingService.getMatchResult(female2);

        System.out.println("male.getMeetingSurvey().getMatchStatus() = " + male.getMeetingSurvey().getMatchStatus());
        System.out.println("female.getMeetingSurvey().getMatchStatus() = " + female.getMeetingSurvey().getMatchStatus());
        System.out.println("female2.getMeetingSurvey().getMatchStatus() = " + female2.getMeetingSurvey().getMatchStatus());

        Assertions.assertEquals(matchResult1.getCode(), 7002);
        Assertions.assertEquals(matchResult2.getCode(), 7003);
        Assertions.assertEquals(matchResult3.getCode(), 7005);
    }
}