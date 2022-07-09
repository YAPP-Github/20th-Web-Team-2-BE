package com.yapp.lonessum.domain.dating.algorithm;

import com.yapp.lonessum.common.algorithm.MatchingAlgorithm;
import com.yapp.lonessum.domain.constant.*;
import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;

import java.util.Arrays;
import java.util.List;

import static com.yapp.lonessum.common.algorithm.AlgorithmUtil.findSameInEachRange;
import static com.yapp.lonessum.common.algorithm.AlgorithmUtil.isValueInRange;

public class DatingMatchingAlgorithm extends MatchingAlgorithm<DatingSurveyDto> {
    @Override
    public <T> int calAllCasesScore(T first, T second) {
        DatingSurveyDto datingSurvey1 = (DatingSurveyDto)first;
        DatingSurveyDto datingSurvey2 = (DatingSurveyDto)second;

        if(!isMatchingTarget(datingSurvey1, datingSurvey2)) {
            return Integer.MIN_VALUE;
        }

        return calAvoidUniversityScore(datingSurvey1, datingSurvey2) +
                calPreferAgeScore(datingSurvey1, datingSurvey2) +
                calPreferHeightScore(datingSurvey1, datingSurvey2) +
                calPreferUniversityScore(datingSurvey1, datingSurvey2) +
                calPreferDepartmentScore(datingSurvey1, datingSurvey2) +
                calPreferBodyScore(datingSurvey1, datingSurvey2) +
                calPreferGameScore(datingSurvey1, datingSurvey2) +
                calPreferDateCountScore(datingSurvey1, datingSurvey2) +
                calPreferCharacteristicScore(datingSurvey1, datingSurvey2);
    }


    //필수 매칭 조건 (남,녀 and 지역)
    private boolean isMatchingTarget(DatingSurveyDto group1, DatingSurveyDto group2) {
        //남,녀 체크
        if(group1.getGender() == group2.getGender()) {
            return false;
        }

        if(group1.getIsAbroad() != group2.getIsAbroad()) {
            return false;
        }

        //해외인 상태
        if(group1.getIsAbroad()) {
            List<Long> group1AbroadAreas = group1.getAbroadAreas();
            List<Long> group2AbroadAreas = group2.getAbroadAreas();

            if(findSameInEachRange(group1AbroadAreas, group2AbroadAreas)) {
                return true;
            }
            return false;
        }

        //국내인 상태
        List<DomesticArea> group1DomesticAreas = group1.getDomesticAreas();
        List<DomesticArea> group2DomesticAreas = group2.getDomesticAreas();

        if(findSameInEachRange(group1DomesticAreas, group2DomesticAreas)) {
            return true;
        }
        return false;
    }

    //기피 학교 점수 계산
    private int calAvoidUniversityScore(DatingSurveyDto user1, DatingSurveyDto user2) {
        Long user1University = user1.getMyUniversity();
        List<Long> user1AvoidUniversities = user1.getAvoidUniversities();

        Long user2University = user2.getMyUniversity();
        List<Long> user2AvoidUniversities = user2.getAvoidUniversities();


        boolean avoidCase1 = findSameInEachRange(Arrays.asList(user1University), user2AvoidUniversities);
        boolean avoidCase2 = findSameInEachRange(Arrays.asList(user2University), user1AvoidUniversities);

        if (avoidCase1 || avoidCase2) {
            return DatingScore.AVOID_UNIVERSITY_SCORE.getScore();
        }

        return DatingScore.ZERO_SCORE.getScore();
    }

    //선호 나이 계산
    private int calPreferAgeScore(DatingSurveyDto user1, DatingSurveyDto user2) {
        Integer user1Age = user1.getAge();
        List<Integer> user2PreferAge = user2.getPreferAge();

        Integer user2Age = user2.getAge();
        List<Integer> user1PreferAge = user1.getPreferAge();

        boolean preferCase1 = isValueInRange(user1Age, user2PreferAge);
        boolean preferCase2 = isValueInRange(user2Age, user1PreferAge);

        if (preferCase1 && preferCase2) {
            return DatingScore.PREFER_AGE_SCORE.getScore();

        }
        return DatingScore.ZERO_SCORE.getScore();
    }

    //선호 체형 계산
    private int calPreferBodyScore(DatingSurveyDto user1, DatingSurveyDto user2) {
        Body user1Body = user1.getMyBody();
        List<Body> user2PreferBodies = user2.getPreferBodies();

        Body user2Body = user2.getMyBody();
        List<Body> user1PreferBodies = user1.getPreferBodies();

        boolean preferCase1 = findSameInEachRange(Arrays.asList(user1Body), user2PreferBodies);
        boolean preferCase2 = findSameInEachRange(Arrays.asList(user2Body), user1PreferBodies);

        if (preferCase1 && preferCase2) {
            return DatingScore.PREFER_BODY_SCORE.getScore();

        }
        return DatingScore.ZERO_SCORE.getScore();
    }

    //선호 키 계산
    private int calPreferHeightScore(DatingSurveyDto user1, DatingSurveyDto user2) {
        Integer user1Height = user1.getMyHeight();
        List<Integer> user2PreferHeight = user2.getPreferHeight();

        Integer user2Height = user2.getMyHeight();
        List<Integer> user1PreferHeight = user1.getPreferHeight();

        boolean preferCase1 = isValueInRange(user1Height, user2PreferHeight);
        boolean preferCase2 = isValueInRange(user2Height, user1PreferHeight);

        if (preferCase1 && preferCase2) {
            return DatingScore.PREFER_HEIGHT_SCORE.getScore();

        }
        return DatingScore.ZERO_SCORE.getScore();
    }

    //선호 연애 횟수 계산
    private int calPreferDateCountScore(DatingSurveyDto user1, DatingSurveyDto user2) {
        DateCount user1DateCount = user1.getMyDateCount();
        DateCount user2PreferDateCount = user2.getPreferDateCount();

        DateCount user2DateCount = user2.getMyDateCount();
        DateCount user1PreferDateCount = user1.getPreferDateCount();

        if (user1DateCount == user2PreferDateCount && user2DateCount == user1PreferDateCount) {
            return DatingScore.PREFER_DATE_COUNT_SCORE.getScore();

        }
        return DatingScore.ZERO_SCORE.getScore();
    }

    //선호 성격 계산
    private int calPreferCharacteristicScore(DatingSurveyDto user1, DatingSurveyDto user2) {
        Characteristic user1Characteristic = user1.getCharacteristic();
        List<Characteristic> user2PreferCharacteristics = user2.getPreferCharacteristics();

        Characteristic user2Characteristic = user2.getCharacteristic();
        List<Characteristic> user1PreferCharacteristics = user1.getPreferCharacteristics();

        boolean preferCase1 = findSameInEachRange(Arrays.asList(user1Characteristic), user2PreferCharacteristics);
        boolean preferCase2 = findSameInEachRange(Arrays.asList(user2Characteristic), user1PreferCharacteristics);

        if (preferCase1 && preferCase2) {
            return DatingScore.PREFER_CHARACTERISTIC_SCORE.getScore();

        }
        return DatingScore.ZERO_SCORE.getScore();
    }

    //선호 학교 계산
    private int calPreferUniversityScore(DatingSurveyDto user1, DatingSurveyDto user2) {
        Long user1University = user1.getMyUniversity();
        List<Long> user2PreferUniversities = user2.getPreferUniversities();

        Long user2University = user2.getMyUniversity();
        List<Long> user1PreferUniversities = user1.getPreferUniversities();

        boolean preferCase1 = findSameInEachRange(Arrays.asList(user1University), user2PreferUniversities);
        boolean preferCase2 = findSameInEachRange(Arrays.asList(user2University), user1PreferUniversities);

        if (preferCase1 && preferCase2) {
            return DatingScore.PREFER_UNIVERSITY_SCORE.getScore();
        }

        return DatingScore.ZERO_SCORE.getScore();
    }

    //선호 학과 계산
    private int calPreferDepartmentScore(DatingSurveyDto user1, DatingSurveyDto user2) {
        Department user1Department = user1.getMyDepartment();
        List<Department> user2PreferDepartments = user2.getPreferDepartments();

        Department user2Department = user2.getMyDepartment();
        List<Department> user1PreferDepartments = user1.getPreferDepartments();

        boolean preferCase1 = findSameInEachRange(Arrays.asList(user1Department), user2PreferDepartments);
        boolean preferCase2 = findSameInEachRange(Arrays.asList(user2Department), user1PreferDepartments);

        if (preferCase1 && preferCase2) {
            return DatingScore.PREFER_DEPARTMENT_SCORE.getScore();
        }

        return DatingScore.ZERO_SCORE.getScore();
    }

    //선호 흡연 여부 계산
    private int calPreferGameScore(DatingSurveyDto user1, DatingSurveyDto user2) {
        Boolean user1Smoke = user1.getMySmoke();
        Boolean user2PreferSmoke = user2.getIsSmokeOk();

        Boolean user2Smoke = user2.getMySmoke();
        Boolean user1PreferSmoke = user1.getIsSmokeOk();

        if(user1Smoke != user2PreferSmoke || user2Smoke != user1PreferSmoke) {
            return DatingScore.AVOID_SMOKE_SCORE.getScore();
        }

        return DatingScore.ZERO_SCORE.getScore();
    }
}
