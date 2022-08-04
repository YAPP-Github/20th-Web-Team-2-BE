package com.yapp.lonessum.domain.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefundTargetDto {
    private String maleId;
    private String femaleId;
    private String payName;
    private Boolean isNeedRefund;
}
