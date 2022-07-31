package com.yapp.lonessum.domain.dating.repository;

import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DatingMatchingRepository extends JpaRepository<DatingMatchingEntity, Long> {
    @Query("select d from DatingMatchingEntity d join fetch d.payment where d.femaleSurvey.id=:surveyId")
    Optional<DatingMatchingEntity> findWithFeMaleSurvey(@Param("surveyId") Long surveyId);
}
