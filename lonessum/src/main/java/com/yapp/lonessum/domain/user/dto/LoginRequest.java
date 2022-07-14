package com.yapp.lonessum.domain.user.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}
