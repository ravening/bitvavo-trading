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
public class Trade {
    private String action;
    private BigDecimal price;
    private BigDecimal quantity;
    private String crypto;
}
