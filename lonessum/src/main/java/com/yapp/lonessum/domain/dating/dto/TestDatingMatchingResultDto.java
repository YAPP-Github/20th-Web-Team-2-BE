package com.yapp.lonessum.domain.dating.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestDatingMatchingResultDto {
    private Long matchId;
    private String maleKakaoId;
    private String femaleKakaoId;
}
