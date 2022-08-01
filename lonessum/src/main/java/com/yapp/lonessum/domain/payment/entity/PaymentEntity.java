package com.yapp.lonessum.domain.payment.entity;

import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String payName;

    private MatchType matchType;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private MeetingMatchingEntity meetingMatching;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private DatingMatchingEntity datingMatching;

    private Boolean isPaid;

    private Boolean isNeedRefund;

    private LocalDateTime paidTime;

    public void updateNeedRefundStatus(Boolean isNeedRefund) {
        this.isNeedRefund = isNeedRefund;
    }

    public void payForMatching(MatchType matchType) {
        this.isPaid = true;
        if (matchType.equals(MatchType.MEETING)) {
            this.meetingMatching.getMaleSurvey().changeMatchStatus(MatchStatus.PAID);
        }
        else {
            this.datingMatching.getMaleSurvey().changeMatchStatus(MatchStatus.PAID);
        }
    }
}
