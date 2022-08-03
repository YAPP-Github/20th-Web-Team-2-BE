package com.yapp.lonessum.domain.meeting.service;

import com.yapp.lonessum.common.dto.SurveyDto;
import com.yapp.lonessum.domain.abroadArea.AbroadAreaEntity;
import com.yapp.lonessum.domain.abroadArea.AbroadAreaRepository;
import com.yapp.lonessum.domain.abroadArea.AbroadAreaService;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.dto.MyMeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.meeting.repository.MeetingSurveyRepository;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import com.yapp.lonessum.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}