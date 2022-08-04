package com.yapp.lonessum.domain.email.repository;

import com.yapp.lonessum.domain.email.entity.EmailToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailTokenRepository extends CrudRepository<EmailToken, String> {
    Optional<EmailToken> findById(String userId);
}
