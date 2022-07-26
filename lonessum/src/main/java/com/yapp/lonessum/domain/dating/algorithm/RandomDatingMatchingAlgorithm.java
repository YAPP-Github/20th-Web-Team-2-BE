package com.yapp.lonessum.domain.dating.algorithm;

import com.yapp.lonessum.common.algorithm.MatchingAlgorithm;
import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;

import java.util.Random;

public class RandomDatingMatchingAlgorithm extends MatchingAlgorithm<DatingSurveyDto> {
    @Override
    public <T> int calAllCasesScore(T first, T second) {
        DatingSurveyDto datingSurvey1 = (DatingSurveyDto)first;
        DatingSurveyDto datingSurvey2 = (DatingSurveyDto)second;

        if(!isMatchingTarget(datingSurvey1, datingSurvey2)) {
            return Integer.MIN_VALUE;
        }

        Random random = new Random();

        return random.nextInt(1000);
    }
}
