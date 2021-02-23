package com.rakeshv.bitvavotrading.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuySellTuple {
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
}
