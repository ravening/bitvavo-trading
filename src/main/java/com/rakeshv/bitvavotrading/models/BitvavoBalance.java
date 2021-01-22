package com.rakeshv.bitvavotrading.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BitvavoBalance {
    private String symbol;
    private String available;
    private String inOrder;

    @Override
    public String toString() {
        return "{" +
                "symbol='" + symbol + '\'' +
                ", available='" + available + '\'' +
                ", inOrder='" + inOrder + '\'' +
                '}';
    }
}
