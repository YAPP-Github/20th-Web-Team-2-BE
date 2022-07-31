package com.yapp.lonessum.domain.payment.entity;

import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@Entity
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String payName;

    private MatchType matchType;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private MeetingMatchingEntity meetingMatching;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private DatingMatchingEntity datingMatching;

    private boolean isPaid;

    private LocalDateTime paidTime;
}
