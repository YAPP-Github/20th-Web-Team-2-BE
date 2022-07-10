package com.yapp.lonessum.domain.payment.entity;

import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private MatchType matchType;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private MeetingSurveyEntity meetingSurvey;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private DatingSurveyEntity datingSurvey;

    private LocalDateTime paidAt;
}
