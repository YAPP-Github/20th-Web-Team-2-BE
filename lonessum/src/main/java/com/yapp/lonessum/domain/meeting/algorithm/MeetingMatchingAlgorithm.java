package com.yapp.lonessum.domain.meeting.algorithm;

import com.yapp.lonessum.common.algorithm.MatchingAlgorithm;
import com.yapp.lonessum.domain.constant.Department;
import com.yapp.lonessum.domain.constant.DomesticArea;
import com.yapp.lonessum.domain.constant.Mindset;
import com.yapp.lonessum.domain.constant.Play;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;

import java.util.List;

import static com.yapp.lonessum.common.algorithm.AlgorithmUtil.findSameInEachRange;
import static com.yapp.lonessum.common.algorithm.AlgorithmUtil.isValueInRange;

public class MeetingMatchingAlgorithm extends MatchingAlgorithm<MeetingSurveyDto> {
    @Override
    public <T> int calAllCasesScore(T first, T second) {
        MeetingSurveyDto meetingSurvey1 = (MeetingSurveyDto)first;
        MeetingSurveyDto meetingSurvey2 = (MeetingSurveyDto)second;

        if(!isMatchingTarget(meetingSurvey1, meetingSurvey2)) {
            return Integer.MIN_VALUE;
        }

        return calAvoidUniversityScore(meetingSurvey1, meetingSurvey2) +
                calPreferAgeScore(meetingSurvey1, meetingSurvey2) +
                calPreferHeightScore(meetingSurvey1, meetingSurvey2) +
                calPreferUniversityScore(meetingSurvey1, meetingSurvey2) +
                calPreferDepartmentScore(meetingSurvey1, meetingSurvey2) +
                calPreferMindsetScore(meetingSurvey1, meetingSurvey2) +
                calPreferGameScore(meetingSurvey1, meetingSurvey2);
    }

    //필수 매칭 조건 (남,녀 and 지역)
    private boolean isMatchingTarget(MeetingSurveyDto group1, MeetingSurveyDto group2) {
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
    private int calAvoidUniversityScore(MeetingSurveyDto group1, MeetingSurveyDto group2) {
        List<Long> group1Universities = group1.getOurUniversities();
        List<Long> group1AvoidUniversities = group1.getAvoidUniversities();

        List<Long> group2Universities = group2.getOurUniversities();
        List<Long> group2AvoidUniversities = group2.getAvoidUniversities();


        boolean avoidCase1 = findSameInEachRange(group1Universities, group2AvoidUniversities);
        boolean avoidCase2 = findSameInEachRange(group2Universities, group1AvoidUniversities);

        if (avoidCase1 || avoidCase2) {
            return MeetingScore.AVOID_UNIVERSITY_SCORE.getScore();
        }

        return MeetingScore.ZERO_SCORE.getScore();
    }

    //선호 나이 계산
    private int calPreferAgeScore(MeetingSurveyDto group1, MeetingSurveyDto group2) {
        Integer group1AverageAge = group1.getAverageAge();
        List<Integer> group2PreferAge = group2.getPreferAge();

        Integer group2AverageAge = group2.getAverageAge();
        List<Integer> group1PreferAge = group1.getPreferAge();

        boolean preferCase1 = isValueInRange(group1AverageAge, group2PreferAge);
        boolean preferCase2 = isValueInRange(group2AverageAge, group1PreferAge);

        if (preferCase1 && preferCase2) {
            return MeetingScore.PREFER_AGE_SCORE.getScore();

        }
        return MeetingScore.ZERO_SCORE.getScore();
    }

    //선호 키 계산
    private int calPreferHeightScore(MeetingSurveyDto group1, MeetingSurveyDto group2) {
        Integer group1AverageHeight = group1.getAverageHeight();
        List<Integer> group2PreferHeight = group2.getPreferHeight();

        Integer group2AverageHeight = group2.getAverageHeight();
        List<Integer> group1PreferHeight = group1.getPreferHeight();

        boolean preferCase1 = isValueInRange(group1AverageHeight, group2PreferHeight);
        boolean preferCase2 = isValueInRange(group2AverageHeight, group1PreferHeight);

        if (preferCase1 && preferCase2) {
            return MeetingScore.PREFER_HEIGHT_SCORE.getScore();

        }
        return MeetingScore.ZERO_SCORE.getScore();
    }

    //선호 학교 계산
    private int calPreferUniversityScore(MeetingSurveyDto group1, MeetingSurveyDto group2) {
        List<Long> group1Universities = group1.getOurUniversities();
        List<Long> group2AvoidUniversities = group2.getAvoidUniversities();

        List<Long> group2Universities = group2.getOurUniversities();
        List<Long> group1AvoidUniversities = group1.getAvoidUniversities();

        boolean preferCase1 = findSameInEachRange(group1Universities, group2AvoidUniversities);
        boolean preferCase2 = findSameInEachRange(group2Universities, group1AvoidUniversities);

        if (preferCase1 && preferCase2) {
            return MeetingScore.PREFER_UNIVERSITY_SCORE.getScore();
        }

        return MeetingScore.ZERO_SCORE.getScore();
    }

    //선호 학과 계산
    private int calPreferDepartmentScore(MeetingSurveyDto group1, MeetingSurveyDto group2) {
        List<Department> group1Departments = group1.getOurDepartments();
        List<Department> group2PreferDepartments = group2.getPreferDepartments();

        List<Department> group2Departments = group2.getOurDepartments();
        List<Department> group1PreferDepartments = group1.getPreferDepartments();

        boolean preferCase1 = findSameInEachRange(group1Departments, group2PreferDepartments);
        boolean preferCase2 = findSameInEachRange(group2Departments, group1PreferDepartments);

        if (preferCase1 && preferCase2) {
            return MeetingScore.PREFER_DEPARTMENT_SCORE.getScore();
        }

        return MeetingScore.ZERO_SCORE.getScore();
    }

    //선호 마인드 셋 계산
    private int calPreferMindsetScore(MeetingSurveyDto group1, MeetingSurveyDto group2) {
        Mindset group1MindSet = group1.getMindSet();
        Mindset group2MindSet = group2.getMindSet();

        if (group1MindSet == Mindset.ALL || group2MindSet == Mindset.ALL) {
            return MeetingScore.MINDSET_SCORE.getScore();
        } else if (group1MindSet == group2MindSet) {
            return MeetingScore.MINDSET_SCORE.getScore();
        }

        return MeetingScore.ZERO_SCORE.getScore();
    }

    //선호 게임 계산
    private int calPreferGameScore(MeetingSurveyDto group1, MeetingSurveyDto group2) {
        Play group1Play = group1.getPlay();
        Play group2Play = group2.getPlay();

        if (group1Play == Play.ALL || group2Play == Play.ALL) {
            return MeetingScore.PLAY_SCORE.getScore();
        } else if (group1Play == group2Play) {
            return MeetingScore.PLAY_SCORE.getScore();
        }

        return MeetingScore.ZERO_SCORE.getScore();
    }
}
