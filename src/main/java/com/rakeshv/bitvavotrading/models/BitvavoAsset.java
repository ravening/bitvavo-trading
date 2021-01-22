package com.rakeshv.bitvavotrading.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitvavoAsset {
    private String symbol;
    private String name;
    private int decimals;
    private String depositFee;
    private int depositConfirmations;
    private String depositStatus;
    private String withdrawalFee;
    private String withdrawalMinAmount;
    private String withdrawalStatus;
    private String message;
}
