package com.yapp.lonessum.domain.meeting.algorithm;

import com.yapp.lonessum.common.algorithm.MatchingInfo;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.yapp.lonessum.domain.meeting.utils.testDataFactory.getTestData;

class MeetingMatchingAlgorithmTest {
    @Test
    @DisplayName("매칭 알고리즘 최적의 결과 도출 테스트")
    public void algorithmTest() {
        MeetingMatchingAlgorithm meetingMatchingAlgorithm = new MeetingMatchingAlgorithm();

        List<MeetingSurveyDto> testData = getTestData();
        List<MatchingInfo<MeetingSurveyDto>> result = meetingMatchingAlgorithm.getResult(testData);

        MeetingSurveyDto first = result.get(0).getFirst();
        System.out.println("first = " + first);
        MeetingSurveyDto second = result.get(0).getSecond();
        System.out.println("second = " + second);

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getScore()).isEqualTo(-17);
    }

}
