package com.yapp.lonessum.domain.meeting.algorithm;


import com.yapp.lonessum.domain.constant.Department;
import com.yapp.lonessum.domain.constant.Mindset;
import com.yapp.lonessum.domain.constant.Play;
import org.springframework.data.util.Pair;

import java.util.*;

public class MeetingMatchingAlgorithm extends MatchingAlgorithm<MeetingSurveyDto> {

    @Override
    public List<MatchingInfo<MeetingSurveyDto>> getResult(List<MeetingSurveyDto> meetingSurveyList) {
        List<MatchingInfo<MeetingSurveyDto>> result = new ArrayList<>();

        boolean[] visited = new boolean[meetingSurveyList.size()];
        Arrays.fill(visited, Boolean.FALSE);

        List<Pair<Integer, Integer>> cases = new ArrayList<>();

        getAllCases(visited, 0, meetingSurveyList.size(), 2, cases);

        for (Pair<Integer, Integer> aCase : cases) {
            int firstIdx = aCase.getFirst();
            int secondIdx = aCase.getSecond();

            MeetingSurveyDto firstSurvey = meetingSurveyList.get(firstIdx);
            MeetingSurveyDto secondSurvey = meetingSurveyList.get(secondIdx);

            int score = calAllCasesScore(firstSurvey, secondSurvey);
            MatchingInfo<MeetingSurveyDto> matchingInfo = new MatchingInfo(score, firstSurvey, secondSurvey);
            result.add(matchingInfo);
        }

        return calOptimalMatchingCase(result);
    }

    private List<MatchingInfo<MeetingSurveyDto>> calOptimalMatchingCase(List<MatchingInfo<MeetingSurveyDto>> cases) {
        List<MatchingInfo<MeetingSurveyDto>> result = new ArrayList<>();
        Map<Long, Boolean> matchingMap = new HashMap<>();

        Collections.sort(cases);

        for (MatchingInfo<MeetingSurveyDto> m : cases) {
            Long id1 = m.getFirst().getId();
            Long id2 = m.getSecond().getId();

            if (matchingMap.containsKey(id1) || matchingMap.containsKey(id2)) {
                continue;
            }
            matchingMap.put(id1, true);
            matchingMap.put(id2, true);

            result.add(m);
        }

        return result;
    }

    private int calAllCasesScore(MeetingSurveyDto meetingSurvey1, MeetingSurveyDto meetingSurvey2) {
        return calAvoidUniversityScore(meetingSurvey1, meetingSurvey2) +
                calPreferAgeScore(meetingSurvey1, meetingSurvey2) +
                calPreferHeightScore(meetingSurvey1, meetingSurvey2) +
                calPreferUniversityScore(meetingSurvey1, meetingSurvey2) +
                calPreferDepartmentScore(meetingSurvey1, meetingSurvey2) +
                calPreferMindsetScore(meetingSurvey1, meetingSurvey2) +
                calPreferGameScore(meetingSurvey1, meetingSurvey2);
    }

    private void getAllCases(boolean[] visited, int start, int n, int r, List<Pair<Integer, Integer>> cases) {
        if (r == 0) {
            Pair<Integer, Integer> order = getOrder(visited);
            cases.add(order);

            return;
        }

        for (int i = start; i < n; i++) {
            visited[i] = true;
            getAllCases(visited, i + 1, n, r - 1, cases);
            visited[i] = false;
        }
    }

    private static Pair<Integer, Integer> getOrder(boolean[] visited) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < visited.length; i++) {
            if (visited[i]) {
                result.add(i);
            }
        }

        return Pair.of(result.get(0), result.get(1));
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

    private <T> boolean findSameInEachRange(List<T> source, List<T> target) {
        return !source.stream()
                .filter(target::contains)
                .findAny()
                .isEmpty();
    }

    //선호 나이 계산
    private int calPreferAgeScore(MeetingSurveyDto group1, MeetingSurveyDto group2) {
        Long group1AverageAge = group1.getAverageAge();
        List<Long> group2PreferAge = group2.getPreferAge();

        Long group2AverageAge = group2.getAverageAge();
        List<Long> group1PreferAge = group1.getPreferAge();

        boolean preferCase1 = isValueInRange(group1AverageAge, group2PreferAge);
        boolean preferCase2 = isValueInRange(group2AverageAge, group1PreferAge);

        if (preferCase1 && preferCase2) {
            return MeetingScore.PREFER_AGE_SCORE.getScore();

        }
        return MeetingScore.ZERO_SCORE.getScore();
    }

    private boolean isValueInRange(Long source, List<Long> target) {
        Long start = target.get(0);
        Long end = target.get(0);

        return start <= source && source <= end;
    }

    //선호 키 계산
    private int calPreferHeightScore(MeetingSurveyDto group1, MeetingSurveyDto group2) {
        Long group1AverageHeight = group1.getAverageHeight();
        List<Long> group2PreferHeight = group2.getPreferHeight();

        Long group2AverageHeight = group2.getAverageHeight();
        List<Long> group1PreferHeight = group1.getPreferHeight();

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