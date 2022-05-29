package com.isloand.userservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class KakaoTokenRequest {
    private String grant_type;
    private String client_id;
    private String redirect_uri;
    private String code;
    private String client_secret;
}
