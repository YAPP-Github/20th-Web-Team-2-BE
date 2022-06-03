package com.isloand.userservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class EmailTokenEntity {
    @Id
    private Long id;

    @OneToOne(mappedBy = "emailToken")
    private UserEntity user;

    private String authCode;

    private LocalDateTime expireDate;
}
