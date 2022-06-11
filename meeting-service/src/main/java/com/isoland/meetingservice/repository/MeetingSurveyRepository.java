package com.isoland.meetingservice.repository;

import com.isoland.meetingservice.entity.MeetingSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingSurveyRepository extends JpaRepository<MeetingSurveyEntity, Long> {
}
