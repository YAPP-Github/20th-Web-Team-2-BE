package com.isloand.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthCodeResponse {
    private String message;
}
