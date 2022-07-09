package com.yapp.lonessum.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PayplePaymentResultResponse {
    @JsonProperty("PCD_PAY_RST")
    private String PCD_PAY_RST;

    @JsonProperty("PCD_PAY_CODE")
    private String PCD_PAY_CODE;

    @JsonProperty("PCD_PAY_MSG")
    private String PCD_PAY_MSG;

    @JsonProperty("PCD_PAY_TYPE")
    private String PCD_PAY_TYPE;

    @JsonProperty("PCD_CARD_VER")
    private String PCD_CARD_VER;

    @JsonProperty("PCD_PAY_WORK")
    private String PCD_PAY_WORK;

    @JsonProperty("PCD_AUTH_KEY")
    private String PCD_AUTH_KEY;

    @JsonProperty("PCD_PAY_REQKEY")
    private String PCD_PAY_REQKEY;

    @JsonProperty("PCD_PAY_HOST")
    private String PCD_PAY_HOST;

    @JsonProperty("PCD_PAY_URL")
    private String PCD_PAY_URL;

    @JsonProperty("PCD_PAY_COFURL")
    private String PCD_PAY_COFURL;

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

    @JsonProperty("PCD_PAY_OID")
    private String PCD_PAY_OID;

    @JsonProperty("PCD_PAY_GOODS")
    private String PCD_PAY_GOODS;

    @JsonProperty("PCD_PAY_AMOUNT")
    private String PCD_PAY_AMOUNT;

    @JsonProperty("PCD_PAY_DISCOUNT")
    private String PCD_PAY_DISCOUNT;

    @JsonProperty("PCD_PAY_AMOUNT_REAL")
    private String PCD_PAY_AMOUNT_REAL;

    @JsonProperty("PCD_PAY_TOTAL")
    private String PCD_PAY_TOTAL;

    @JsonProperty("PCD_PAY_TAXTOTAL")
    private String PCD_PAY_TAXTOTAL;

    @JsonProperty("PCD_PAY_ISTAX")
    private String PCD_PAY_ISTAX;

    @JsonProperty("PCD_PAYER_CARDNAME")
    private String PCD_PAYER_CARDNAME;

    @JsonProperty("PCD_PAYER_CARDNUM")
    private String PCD_PAYER_CARDNUM;

    @JsonProperty("PCD_PAYER_CARDQUOTA")
    private String PCD_PAYER_CARDQUOTA;

    @JsonProperty("PCD_PAYER_CARDTRADENUM")
    private String PCD_PAYER_CARDTRADENUM;

    @JsonProperty("PCD_PAYER_CARDAUTHNO")
    private String PCD_PAYER_CARDAUTHNO;

    @JsonProperty("PCD_PAYER_CARDRECEIPT")
    private String PCD_PAYER_CARDRECEIPT;

    @JsonProperty("PCD_PAY_TIME")
    private String PCD_PAY_TIME;

    @JsonProperty("PCD_SIMPLE_FLAG")
    private String PCD_SIMPLE_FLAG;

    @JsonProperty("PCD_RST_URL")
    private String PCD_RST_URL;

    @JsonProperty("PCD_PAY_BANKACCTYPE")
    private String PCD_PAY_BANKACCTYPE;

    @JsonProperty("PCD_PAY_BANK")
    private String PCD_PAY_BANK;

    @JsonProperty("PCD_PAY_BANKNAME")
    private String PCD_PAY_BANKNAME;

    @JsonProperty("PCD_PAY_BANKNUM")
    private String PCD_PAY_BANKNUM;

    @JsonProperty("PCD_TAXSAVE_MGTNUM")
    private String PCD_TAXSAVE_MGTNUM;
}
