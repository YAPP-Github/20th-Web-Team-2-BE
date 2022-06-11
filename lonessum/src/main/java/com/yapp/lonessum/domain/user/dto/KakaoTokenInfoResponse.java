package com.yapp.lonessum.domain.user.dto;

import lombok.Data;

@Data
public class KakaoTokenInfoResponse {
    private long id;
    private int expires_in;
    private int app_id;
}
