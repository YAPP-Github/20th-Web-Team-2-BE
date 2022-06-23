package com.yapp.lonessum.domain.meeting.repository;

import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingSurveyRepository extends JpaRepository<MeetingSurveyEntity, Long> {
    Optional<List<MeetingSurveyEntity>> findAllByMatchStatus(MatchStatus matchStatus);
}
