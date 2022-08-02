package com.yapp.lonessum.domain.email.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@Builder
@RedisHash(value = "emailToken", timeToLive = 180L)
public class EmailToken implements Serializable {
    @Id
    private String userId;
    @Indexed
    private String authCode;
}
