package com.yapp.lonessum.domain.meeting.scheduler;

import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingMatchingRepository;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.yapp.lonessum.domain.meeting.utils.testDataFactory.getTestDataEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MeetingMatchingSchedulerTest {

    @Autowired
    MeetingMatchingScheduler meetingMatchingScheduler;

    @Autowired
    MeetingSurveyRepository meetingSurveyRepository;
    
    @Autowired
    MeetingMatchingRepository meetingMatchingRepository;

    @Test
    @Transactional
    void runMatch() {
        //given
        List<MeetingSurveyEntity> surveyList = getTestDataEntity();
        surveyList.forEach((meetingSurvey -> {
            meetingSurveyRepository.save(meetingSurvey);
        }));

        //when
        meetingMatchingScheduler.runMatch();

//        meetingSurveyRepository.findAll().forEach((meetingSurvey -> {
//            System.out.println("meetingSurvey.getId() = " + meetingSurvey.getId());
//        }));
//
//        meetingMatchingRepository.findAll().forEach((meetingMatching -> {
//            System.out.println("meetingMatching.getId() = " + meetingMatching.getId());
//        }));

        Assertions.assertEquals(surveyList.get(0).getMatchStatus(), MatchStatus.MATCHED);
        Assertions.assertEquals(surveyList.get(1).getMatchStatus(), MatchStatus.PAID);
        Assertions.assertEquals(surveyList.get(2).getMatchStatus(), MatchStatus.FAILED);

        Assertions.assertEquals(surveyList.get(0).getMeetingMatching().getPayment().getIsPaid(), false);
    }

    @Test
    void matchedToFailed() {
    }
}