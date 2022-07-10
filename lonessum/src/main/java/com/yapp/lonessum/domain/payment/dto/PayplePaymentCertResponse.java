package com.yapp.lonessum.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PayplePaymentCertResponse {
    @JsonProperty("PCD_PAY_RST")
    private String PCD_PAY_RST;

    @JsonProperty("PCD_PAY_CODE")
    private String PCD_PAY_CODE;

    @JsonProperty("PCD_PAY_MSG")
    private String PCD_PAY_MSG;

    @JsonProperty("PCD_PAY_TYPE")
    private String PCD_PAY_TYPE;

    @JsonProperty("PCD_PAY_REQKEY")
    private String PCD_PAY_REQKEY;

    @JsonProperty("PCD_PAY_OID")
    private String PCD_PAY_OID;

    @JsonProperty("PCD_PAYER_ID")
    private String PCD_PAYER_ID;

    @JsonProperty("PCD_PAYER_NO")
    private String PCD_PAYER_NO;

    @JsonProperty("PCD_PAYER_NAME")
    private String PCD_PAYER_NAME;

    @JsonProperty("PCD_PAYER_HP")
    private String PCD_PAYER_HP;

    @JsonProperty("PCD_PAYER_EMAIL")
    private String PCD_PAYER_EMAIL;

    @JsonProperty("PCD_PAY_GOODS")
    private String PCD_PAY_GOODS;

    @JsonProperty("PCD_PAY_TOTAL")
    private String PCD_PAY_TOTAL;

    @JsonProperty("PCD_PAY_TAXTOTAL")
    private String PCD_PAY_TAXTOTAL;

    @JsonProperty("PCD_PAY_ISTAX")
    private String PCD_PAY_ISTAX;

    @JsonProperty("PCD_PAY_TIME")
    private String PCD_PAY_TIME;

    @JsonProperty("PCD_PAYER_CARDNAME")
    private String PCD_PAYER_CARDNAME;

    @JsonProperty("PCD_PAYER_CARDNUM")
    private String PCD_PAYER_CARDNUM;

    @JsonProperty("PCD_PAYER_CARDTRADENUM")
    private String PCD_PAYER_CARDTRADENUM;

    @JsonProperty("PCD_PAYER_CARDAUTHNO")
    private String PCD_PAYER_CARDAUTHNO;

    @JsonProperty("PCD_PAYER_CARDRECEIPT")
    private String PCD_PAYER_CARDRECEIPT;

    @JsonProperty("PCD_SIMPLE_FLAG")
    private String PCD_SIMPLE_FLAG;

    @JsonProperty("PCD_PAY_BANK")
    private String PCD_PAY_BANK;

    @JsonProperty("PCD_PAY_BANKNAME")
    private String PCD_PAY_BANKNAME;

    @JsonProperty("PCD_PAY_BANKNUM")
    private String PCD_PAY_BANKNUM;

    @JsonProperty("PCD_TAXSAVE_MGTNUM")
    private String PCD_TAXSAVE_MGTNUM;

    @JsonProperty("PCD_TAXSAVE_FLAG")
    private String PCD_TAXSAVE_FLAG;

    @JsonProperty("PCD_TAXSAVE_RST")
    private String PCD_TAXSAVE_RST;
}
