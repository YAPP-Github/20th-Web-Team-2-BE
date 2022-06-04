package com.isloand.userservice.dto;

import lombok.Data;

@Data
public class KakaoTokenInfoResponse {
    private long id;
    private int expires_in;
    private int app_id;
}
