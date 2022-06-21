package com.yapp.lonessum.common.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MatchingInfo<T> implements Comparable<MatchingInfo> {
    private int score;
    private T first;
    private T second;

    @Override
    public int compareTo(MatchingInfo o) {
        return o.getScore() - score;
    }
}
