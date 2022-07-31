package com.yapp.lonessum.domain.payment.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayName implements Serializable {
    private String payName;
    private Boolean isUsing;
}
