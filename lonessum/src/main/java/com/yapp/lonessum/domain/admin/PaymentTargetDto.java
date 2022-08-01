package com.yapp.lonessum.domain.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentTargetDto {
    private String maleId;
    private String femaleId;
    private String payName;
}
