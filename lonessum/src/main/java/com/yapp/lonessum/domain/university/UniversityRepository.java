package com.yapp.lonessum.domain.university;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {
    boolean existsByDomain(String domain);
    UniversityEntity findByDomain(String domain);


}
