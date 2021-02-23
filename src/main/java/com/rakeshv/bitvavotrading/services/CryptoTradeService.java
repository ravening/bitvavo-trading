package com.rakeshv.bitvavotrading.services;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CryptoTradeService {

    public abstract void checkBuySell(String price);

    void sell(String price) {
        log.error("Selling crypto at {}", price);
    }

    void buy(String price) {
        log.error("Buying crypto at {}", price);
    }
}
