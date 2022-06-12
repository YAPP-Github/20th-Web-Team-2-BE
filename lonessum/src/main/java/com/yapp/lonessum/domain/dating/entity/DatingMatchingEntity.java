package com.yapp.lonessum.domain.dating.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dating_matching")
public class DatingMatchingEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private DatingMatchingEntity surveyA;

    @ManyToOne(fetch = FetchType.LAZY)
    private DatingMatchingEntity surveyB;

    private Boolean isPaidA;

    private Boolean isPaidB;

    private LocalDateTime matchedAt;
}
