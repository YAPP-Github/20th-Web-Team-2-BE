package com.yapp.lonessum.domain.user.repository;

import com.yapp.lonessum.domain.user.redis.UserJwtObject;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisRepository  extends CrudRepository<UserJwtObject, String> {
    Optional<UserJwtObject> findByJwt(String jwt);
}
