package com.yapp.lonessum.common.algorithm;

import java.util.List;

public class AlgorithmUtil {
    public static <T> boolean findSameInEachRange(List<T> source, List<T> target) {
        return !source.stream()
                .filter(target::contains)
                .findAny()
                .isEmpty();
    }

    public static boolean isValueInRange(Long source, List<Long> target) {
        Long start = target.get(0);
        Long end = target.get(0);

        return start <= source && source <= end;
    }

}
