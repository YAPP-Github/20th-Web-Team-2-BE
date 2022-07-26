package com.yapp.lonessum.domain.meeting.algorithm;

import com.yapp.lonessum.common.algorithm.MatchingAlgorithm;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;

import java.util.Random;

public class RandomMeetingMatchingAlgorithm extends MatchingAlgorithm<MeetingSurveyDto> {
    @Override
    public <T> int calAllCasesScore(T first, T second) {
        MeetingSurveyDto meetingSurvey1 = (MeetingSurveyDto)first;
        MeetingSurveyDto meetingSurvey2 = (MeetingSurveyDto)second;

        if(!isMatchingTarget(meetingSurvey1, meetingSurvey2)) {
            return Integer.MIN_VALUE;
        }

        Random random = new Random();

        return random.nextInt(1000);
    }
}
