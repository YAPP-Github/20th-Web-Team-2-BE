package com.isoland.meetingservice.repository;

import com.isoland.meetingservice.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {
}
