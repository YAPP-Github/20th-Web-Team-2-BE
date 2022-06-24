package com.yapp.lonessum.domain.user.repository;

import com.yapp.lonessum.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByKakaoServerId(Long kakaoServerId);
}
