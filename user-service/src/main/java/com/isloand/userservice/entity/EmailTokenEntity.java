package com.isloand.userservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class EmailTokenEntity {
    @Id
    private Long id;

    private Long userId;

    private String authCode;

    private Boolean isExpired;

    private LocalDateTime expireDate;
}
