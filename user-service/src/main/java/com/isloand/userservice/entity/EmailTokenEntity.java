package com.isloand.userservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class EmailTokenEntity {
    @Id
    private Long id;

    @OneToOne(mappedBy = "emailToken")
    private UserEntity user;

    private String authCode;

    private LocalDateTime expireDate;
}
