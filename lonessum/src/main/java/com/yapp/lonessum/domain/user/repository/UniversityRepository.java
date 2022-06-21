package com.yapp.lonessum.domain.user.repository;

import com.yapp.lonessum.domain.user.entity.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {
    boolean existsByDomain(String domain);
    UniversityEntity findByDomain(String domain);
}
