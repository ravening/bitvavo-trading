package com.rakeshv.bitvavotrading.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BitvavoTickerPrice {
    private String market;
    private String price;
}
