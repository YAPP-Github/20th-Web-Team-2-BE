package com.yapp.lonessum.domain.email.repository;

import com.yapp.lonessum.domain.email.entity.EmailTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTokenRepository extends JpaRepository<EmailTokenEntity, Long> {
}
