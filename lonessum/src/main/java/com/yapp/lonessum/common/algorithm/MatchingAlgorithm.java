package com.yapp.lonessum.common.algorithm;

import com.yapp.lonessum.common.dto.SurveyDto;
import org.springframework.data.util.Pair;

import java.util.*;

public abstract class MatchingAlgorithm<T> {
    public List<MatchingInfo<T>> getResult(List<T> surveyList) {
        List<MatchingInfo<T>> result = new ArrayList<>();

        boolean[] visited = new boolean[surveyList.size()];
        Arrays.fill(visited, Boolean.FALSE);

        List<Pair<Integer, Integer>> cases = new ArrayList<>();

        getAllCases(visited, 0, surveyList.size(), 2, cases);

        for (Pair<Integer, Integer> aCase : cases) {
            int firstIdx = aCase.getFirst();
            int secondIdx = aCase.getSecond();

            T firstSurvey = surveyList.get(firstIdx);
            T secondSurvey = surveyList.get(secondIdx);

            int score = calAllCasesScore(firstSurvey, secondSurvey);
            MatchingInfo<T> matchingInfo = new MatchingInfo(score, firstSurvey, secondSurvey);
            result.add(matchingInfo);
        }

        return calOptimalMatchingCase(result);
    }

    public List<MatchingInfo<T>> calOptimalMatchingCase(List<MatchingInfo<T>> cases) {
        List<MatchingInfo<T>> result = new ArrayList<>();
        Map<Long, Boolean> matchingMap = new HashMap<>();

        Collections.sort(cases);

        for (MatchingInfo<T> m : cases) {
            Long id1 = ((SurveyDto)m.getFirst()).getId();
            Long id2 = ((SurveyDto)m.getSecond()).getId();

            if (matchingMap.containsKey(id1) || matchingMap.containsKey(id2)) {
                continue;
            }
            matchingMap.put(id1, true);
            matchingMap.put(id2, true);

            result.add(m);
        }

        return result;
    }

    public abstract <T> int calAllCasesScore(T first, T second);

    public void getAllCases(boolean[] visited, int start, int n, int r, List<Pair<Integer, Integer>> cases) {
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
}
