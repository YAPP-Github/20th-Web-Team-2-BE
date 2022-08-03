package com.yapp.lonessum.domain.dating.entity;

import com.yapp.lonessum.domain.payment.entity.PaymentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dating_matching")
public class DatingMatchingEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private DatingSurveyEntity maleSurvey;

    @OneToOne(fetch = FetchType.LAZY)
    private DatingSurveyEntity femaleSurvey;

    @OneToOne(fetch = FetchType.LAZY)
    private PaymentEntity payment;

    private LocalDateTime matchedTime;

    public void changePayment(PaymentEntity payment) {
        this.payment = payment;
    }
}
