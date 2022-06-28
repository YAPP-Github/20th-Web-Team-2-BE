package com.yapp.lonessum.domain.dating.repository;

import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatingMatchingRepository extends JpaRepository<DatingMatchingEntity, Long> {
}
