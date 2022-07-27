package com.yapp.lonessum.domain.email.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@Builder
@RedisHash(value = "emailToken", timeToLive = 180)
public class EmailToken implements Serializable {
    @Id
    private Long id;
    @Indexed
    private Long userId;
    private String authCode;
}
