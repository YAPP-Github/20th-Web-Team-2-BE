package com.isloand.userservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class UserEntity {
    @Id
    private Long id;

    private String kakaoEmail;

    private String universityEmail;

    @OneToOne
    @JoinColumn(name = "email_token_entity_id")
    private EmailTokenEntity emailToken;

    private Boolean isAuthenticated;
}
