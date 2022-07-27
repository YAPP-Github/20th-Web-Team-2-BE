package com.yapp.lonessum.domain.email.entity;

import com.yapp.lonessum.domain.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email_token")
public class EmailTokenEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "emailToken")
    private UserEntity user;

    private String authCode;

    private LocalDateTime expireDate;
}
