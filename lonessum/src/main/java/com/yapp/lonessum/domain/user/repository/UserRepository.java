package com.yapp.lonessum.domain.user.repository;

import com.yapp.lonessum.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByKakaoServerId(Long kakaoServerId);
    Optional<UserEntity> findByUserName(String userName);

    @Query("select u from UserEntity u left join fetch u.datingSurvey left join fetch u.meetingSurvey where u.id=:userId")
    Optional<UserEntity> findUserWithSurveys(Long userId);
}
