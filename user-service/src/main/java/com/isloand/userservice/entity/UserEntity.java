package com.isloand.userservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserEntity {
    @Id
    private Long id;

    private String kakaoEmail;

    private String universityEmail;

    private String university;

    private Boolean isAuthenticated;
}
