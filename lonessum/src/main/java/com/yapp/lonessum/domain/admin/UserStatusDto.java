package com.yapp.lonessum.domain.admin;

import com.yapp.lonessum.domain.constant.MatchStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatusDto {
    private String kakaoId;
    private MatchStatus matchStatus;
    private boolean isPaid;
}
