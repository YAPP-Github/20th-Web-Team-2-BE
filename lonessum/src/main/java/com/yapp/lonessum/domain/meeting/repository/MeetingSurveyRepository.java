package com.yapp.lonessum.domain.meeting.repository;

import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingSurveyRepository extends JpaRepository<MeetingSurveyEntity, Long> {
}
