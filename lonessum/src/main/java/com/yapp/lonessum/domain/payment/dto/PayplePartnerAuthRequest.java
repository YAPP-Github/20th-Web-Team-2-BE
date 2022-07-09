package com.yapp.lonessum.domain.payment.dto;

import lombok.Data;

@Data
public class PayplePartnerAuthRequest {
    private String cst_id;
    private String custKey;
}
