package com.yapp.lonessum.domain.user.service;

import com.yapp.lonessum.domain.user.redis.UserJwtObject;
import com.yapp.lonessum.domain.user.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListService {
    private final RedisRepository redisRepository;

    public String registerBlackList(String userId, String jwt) {
        redisRepository.save(UserJwtObject.builder()
                .userId(userId)
                .jwt(jwt)
                .build());

        return userId;
    }

    public Boolean isJwtInBlackList(String jwt) {
        return redisRepository.findByJwt(jwt).isPresent();
    }
}
