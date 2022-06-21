package com.yapp.lonessum.domain.dating.algorithm;

import com.yapp.lonessum.common.algorithm.MatchingInfo;
import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.yapp.lonessum.domain.dating.utils.testDataFactory.getTestData;

class DatingMatchingAlgorithmTest {
    @Test
    @DisplayName("데이팅 매칭 알고리즘 최적의 결과 도출 테스트")
    public void algorithmTest() {
        DatingMatchingAlgorithm DatingMatchingAlgorithm = new DatingMatchingAlgorithm();

        List<DatingSurveyDto> testData = getTestData();
        List<MatchingInfo<DatingSurveyDto>> result = DatingMatchingAlgorithm.getResult(testData);

        DatingSurveyDto first = result.get(0).getFirst();
        System.out.println("first = " + first);
        DatingSurveyDto second = result.get(0).getSecond();
        System.out.println("second = " + second);

        Assertions.assertThat(1).isEqualTo(result.size());
        Assertions.assertThat(20).isEqualTo(result.get(0).getScore());
    }
}
