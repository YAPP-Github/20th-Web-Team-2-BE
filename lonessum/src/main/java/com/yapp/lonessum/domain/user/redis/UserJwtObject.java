package com.yapp.lonessum.domain.user.redis;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@Builder
@RedisHash(value = "blacklist", timeToLive = 60*24)
public class UserJwtObject implements Serializable {
    @Id
    private String userId;
    @Indexed
    private String jwt;
}
