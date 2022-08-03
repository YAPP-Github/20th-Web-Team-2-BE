package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.common.dto.SurveyDto;
import com.yapp.lonessum.domain.abroadArea.AbroadAreaEntity;
import com.yapp.lonessum.domain.abroadArea.AbroadAreaRepository;
import com.yapp.lonessum.domain.abroadArea.AbroadAreaService;
import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.dto.MyMeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.meeting.scheduler.MeetingMatchingScheduler;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.yapp.lonessum.domain.meeting.utils.testDataFactory.getTestDataEntity;

@SpringBootTest
class MeetingSurveyServiceTest {

    @Autowired
    MeetingSurveyService meetingSurveyService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AbroadAreaRepository abroadAreaRepository;

    @Autowired
    MeetingSurveyRepository meetingSurveyRepository;

    @Autowired
    MeetingMatchingScheduler meetingMatchingScheduler;

    @Test
    void readSurvey() {
        UserEntity user = UserEntity.builder()
                .build();
        userRepository.save(user);

        AbroadAreaEntity area = new AbroadAreaEntity();
        area.setName("korea");
        abroadAreaRepository.save(area);

        List<MeetingSurveyEntity> testDataList = getTestDataEntity();
        testDataList.forEach((meetingSurvey -> {
            meetingSurveyRepository.save(meetingSurvey);
        }));

        user.changeMeetingSurvey(testDataList.get(0));

        MyMeetingSurveyDto myMeetingSurveyDto = meetingSurveyService.readSurvey(user);
        System.out.println("myMeetingSurveyDto.getStringAbroadAreas() = " + myMeetingSurveyDto.getStringAbroadAreas());
    }

    @Test
    @Transactional
    public void 모든_설문을_대기상태로_변경() {
        getTestDataEntity().forEach((meetingSurvey -> {
            meetingSurveyRepository.save(meetingSurvey);
        }));

        meetingMatchingScheduler.runMatch();

        meetingSurveyRepository.findAll().forEach((meetingSurvey -> {
            Assertions.assertThat(meetingSurvey.getMatchStatus()).isNotEqualTo(MatchStatus.WAITING);
        }));

        meetingSurveyService.rollBackToWaiting();

        meetingSurveyRepository.findAll().forEach((meetingSurvey -> {
            Assertions.assertThat(meetingSurvey.getMatchStatus()).isEqualTo(MatchStatus.WAITING);
        }));
    }
}