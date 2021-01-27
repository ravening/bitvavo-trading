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
public class BitvavoBtcPrice {
    private String market;
    private String bestAsk;
    private String bestAskSize;
    private String bestBid;
    private String bestBidSize;
    private String lastPrice;

    @Override
    public String toString() {
        return "BestAsk='" + bestAsk + '\'' +
                ", BestAskSize='" + bestAskSize + '\'' +
                ", BestBid='" + bestBid + '\'' +
                ", BestBidSize='" + bestBidSize + '\'' +
                ", LastPrice='" + lastPrice + '\'';
    }
}
