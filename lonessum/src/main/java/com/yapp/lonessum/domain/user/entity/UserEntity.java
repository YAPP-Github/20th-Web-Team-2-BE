package com.yapp.lonessum.domain.user.entity;

import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private String universityEmail;

    @OneToOne(fetch = FetchType.LAZY)
    private EmailTokenEntity emailToken;

    private Boolean isAuthenticated;

    @OneToMany(mappedBy = "user")
    private List<MeetingSurveyEntity> meetingSurveyEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<DatingSurveyEntity> datingSurveyEntityList = new ArrayList<>();

    public void registerUniversityEmail(String universityEmail) {
        this.universityEmail = universityEmail;
    }

    public void authenticatedWithEmail() {
        this.isAuthenticated = true;
    }

    public void issueEmailToken(EmailTokenEntity emailToken) {
        this.emailToken = emailToken;
    }
}
