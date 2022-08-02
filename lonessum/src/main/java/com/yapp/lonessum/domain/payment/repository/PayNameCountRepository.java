package com.yapp.lonessum.domain.payment.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PayNameCountRepository {

    private final String key = "payNameCounter";
    private final RedisTemplate<String, String> redisTemplate;

    public Long getPayNameCounter() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return Long.parseLong(valueOperations.get(key));
    }

    public void setPayNameCounter(Integer count) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, count.toString());
    }

    public Integer countWithPreValue() {
        String curCnt = redisTemplate.opsForValue().get(key);
        redisTemplate.opsForValue().set(key, String.valueOf(Integer.parseInt(curCnt) + 1));

        return Integer.parseInt(curCnt);
    }
}
