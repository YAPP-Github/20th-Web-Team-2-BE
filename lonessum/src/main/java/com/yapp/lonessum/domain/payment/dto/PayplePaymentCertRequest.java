package com.yapp.lonessum.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PayplePaymentCertRequest {
    @JsonProperty("PCD_CST_ID")
    private String PCD_CST_ID;

    @JsonProperty("PCD_CUST_KEY")
    private String PCD_CUST_KEY;

    @JsonProperty("PCD_AUTH_KEY")
    private String PCD_AUTH_KEY;

    @JsonProperty("PCD_PAY_REQKEY")
    private String PCD_PAY_REQKEY;

    @JsonProperty("PCD_PAYER_ID")
    private String PCD_PAYER_ID;
}
