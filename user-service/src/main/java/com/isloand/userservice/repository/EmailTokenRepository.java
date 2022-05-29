package com.isloand.userservice.repository;

import com.isloand.userservice.domain.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {
}
