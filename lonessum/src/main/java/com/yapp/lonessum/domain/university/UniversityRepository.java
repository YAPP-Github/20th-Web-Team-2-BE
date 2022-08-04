package com.yapp.lonessum.domain.university;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {
    boolean existsByDomain(String domain);
     Optional<UniversityEntity> findByDomain(String domain);


}
