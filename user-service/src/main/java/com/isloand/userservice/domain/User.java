package com.isloand.userservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private Long id;

    private String kakaoEmail;

    private String universityEmail;

    private String university;

    private Boolean isAuthenticated;
}
