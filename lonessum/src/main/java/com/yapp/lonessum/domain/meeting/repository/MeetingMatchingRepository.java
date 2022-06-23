package com.yapp.lonessum.domain.meeting.repository;

import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMatchingRepository extends JpaRepository<MeetingMatchingEntity, Long> {
}
