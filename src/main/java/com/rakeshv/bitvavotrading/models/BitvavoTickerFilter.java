package com.rakeshv.bitvavotrading.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BitvavoTickerFilter {
    private String market;

    @Override
    public String toString() {
        return "{ market: " + market + " }";
    }
}
