package com.isloand.userservice.entity;

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
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String kakaoEmail;

    private String universityEmail;

    @OneToOne
    @JoinColumn(name = "email_token_entity_id")
    private EmailTokenEntity emailToken;

    private Boolean isAuthenticated;

    public void registerUniversityEmail(String universityEmail) {
        this.universityEmail = universityEmail;
    }
}
