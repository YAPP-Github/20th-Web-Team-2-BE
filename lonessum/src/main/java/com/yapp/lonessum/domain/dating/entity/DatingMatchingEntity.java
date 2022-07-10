package com.yapp.lonessum.domain.dating.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
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

    private LocalDateTime matchedAt;
}
