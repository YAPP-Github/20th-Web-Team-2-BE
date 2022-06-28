package com.yapp.lonessum.domain.user.entity;

import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long kakaoServerId;
  
    @ManyToOne(fetch = FetchType.LAZY)
    private UniversityEntity university;

    private String kakaoEmail;

    private String universityEmail;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private EmailTokenEntity emailToken;

    private Boolean isAuthenticated;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private MeetingSurveyEntity meetingSurvey;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private DatingSurveyEntity datingSurvey;

    public void registerUniversityEmail(String universityEmail) {
        this.universityEmail = universityEmail;
    }

    public void authenticatedWithEmail(UniversityEntity university) {
        this.university = university;
        this.isAuthenticated = true;
    }

    public void issueEmailToken(EmailTokenEntity emailToken) {
        this.emailToken = emailToken;
    }

    public void changeMeetingSurvey(MeetingSurveyEntity meetingSurvey) {
        this.meetingSurvey = meetingSurvey;
    }

    public void changeDatingSurvey(DatingSurveyEntity datingSurvey) {
        this.datingSurvey = datingSurvey;
    }
}
