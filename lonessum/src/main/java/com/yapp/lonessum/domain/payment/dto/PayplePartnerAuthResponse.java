package com.yapp.lonessum.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PayplePartnerAuthResponse {
    private String server_name;
    private String result;
    private String result_msg;
    private String cst_id;
    private String custKey;
    @JsonProperty("AuthKey")
    private String AuthKey;
    @JsonProperty("PCD_PAY_HOST")
    private String PCD_PAY_HOST;
    @JsonProperty("PCD_PAY_URL")
    private String PCD_PAY_URL;
    private String return_url;
}
