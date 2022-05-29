package com.isloand.userservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class EmailToken {
    @Id
    private Long id;

    private Long userId;

    private String authCode;

    private Boolean isExpired;

    private LocalDateTime expireDate;
}
