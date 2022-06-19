package com.yapp.lonessum.common.algorithm;

import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class MatchingAlgorithm<T> {
    public List<MatchingInfo<T>> getResult(List<T> list) {
        return null;
    }

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
