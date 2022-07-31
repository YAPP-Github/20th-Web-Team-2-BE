package com.yapp.lonessum.domain.meeting.repository;

import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MeetingMatchingRepository extends JpaRepository<MeetingMatchingEntity, Long> {
    @Query("select m from MeetingMatchingEntity m join fetch m.payment where m.femaleSurvey.id=:surveyId")
    Optional<MeetingMatchingEntity> findWithFeMaleSurvey(@Param("surveyId") Long surveyId);
}
