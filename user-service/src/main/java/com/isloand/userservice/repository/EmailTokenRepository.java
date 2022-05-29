package com.isloand.userservice.repository;

import com.isloand.userservice.entity.EmailTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTokenRepository extends JpaRepository<EmailTokenEntity, Long> {
}
