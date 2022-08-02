package com.yapp.lonessum.domain.payment.repository;

import com.yapp.lonessum.domain.payment.entity.PayName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PayNameRepository {

    private final String key = "payName";
    private final RedisTemplate<String, String> redisTemplate;

    public PayName findByPayNameId(Long payNameId) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        return (PayName) hashOperations.get(key, payNameId);
    }

    public void updatePayName(Long payNameId, PayName payName) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, payNameId, payName);
    }
}
