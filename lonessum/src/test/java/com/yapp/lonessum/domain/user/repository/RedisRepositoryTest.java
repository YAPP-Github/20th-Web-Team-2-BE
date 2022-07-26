package com.yapp.lonessum.domain.user.repository;

import com.yapp.lonessum.domain.user.redis.UserJwtObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class RedisRepositoryTest {
    @Autowired RedisRepository redisRepository;

    @Test
    @DisplayName("Redis에 값이 정상적으로 저장되는지 확인")
    public void saveTest() {
        //given
        UserJwtObject userJwtObject = UserJwtObject.builder()
                .userId("testUserId")
                .jwt("testJwt")
                .build();

        redisRepository.save(userJwtObject);

        //when
        UserJwtObject testUserId = redisRepository.findById("testUserId").get();

        //then
        Assertions.assertEquals("testJwt", testUserId.getJwt());
    }

    @Test
    @DisplayName("jwt기반으로 조회 여부 확인")
    public void retrieveByJwtTest() {
        //given
        UserJwtObject userJwtObject = UserJwtObject.builder()
                .userId("testUserId")
                .jwt("testJwt")
                .build();

        redisRepository.save(userJwtObject);

        //when
        UserJwtObject testUserId = redisRepository.findByJwt("testJwt").get();

        //then
        Assertions.assertEquals("testJwt", testUserId.getJwt());
    }
}
