package com.yapp.lonessum.domain.user.entity;

import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import com.yapp.lonessum.domain.university.UniversityEntity;
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

    private String userName;

    private String password;

    private Long kakaoServerId;
  
    @ManyToOne(fetch = FetchType.LAZY)
    private UniversityEntity university;

    private String universityEmail;

    private Boolean isAuthenticated;

    private Boolean isAdult;

    @OneToOne(fetch = FetchType.LAZY)
    private MeetingSurveyEntity meetingSurvey;

    @OneToOne(fetch = FetchType.LAZY)
    private DatingSurveyEntity datingSurvey;

    public void registerUniversityEmail(String universityEmail) {
        this.universityEmail = universityEmail;
    }

    public void authenticatedWithEmail(UniversityEntity university) {
        this.university = university;
        this.isAuthenticated = true;
    }

    public boolean changeIsAdult(Boolean isAdult) {
        this.isAdult = isAdult;
        return isAdult;
    }

    public void changeMeetingSurvey(MeetingSurveyEntity meetingSurvey) {
        this.meetingSurvey = meetingSurvey;
        meetingSurvey.changeUser(this);
    }

    public void changeDatingSurvey(DatingSurveyEntity datingSurvey) {
        this.datingSurvey = datingSurvey;
        datingSurvey.changeUser(this);
    }
}
