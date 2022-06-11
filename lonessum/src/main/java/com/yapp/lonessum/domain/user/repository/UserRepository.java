package com.yapp.lonessum.domain.user.repository;

import com.yapp.lonessum.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
