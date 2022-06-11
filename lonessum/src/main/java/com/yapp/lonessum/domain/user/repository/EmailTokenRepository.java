package com.yapp.lonessum.domain.user.repository;

import com.yapp.lonessum.domain.user.entity.EmailTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTokenRepository extends JpaRepository<EmailTokenEntity, Long> {
}
