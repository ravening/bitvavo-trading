package com.rakeshv.bitvavotrading.services;

import com.rakeshv.bitvavotrading.models.BuySellTuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("BTC")
@Slf4j
public class BtcCryptoTradeService extends CryptoTradeService {

    @Autowired
    BitvavoTradeService bitvavoTradeService;

    @Override
    public void checkBuySell(String price) {
        BigDecimal currentPrice = new BigDecimal(price);
        BuySellTuple buySellTuple = bitvavoTradeService.getBuySellTupleForCrypto("BTC");

        if (buySellTuple != null) {
            if (currentPrice.compareTo(buySellTuple.getBuyPrice()) < 0) {
                buy(price);
            } else if (currentPrice.compareTo(buySellTuple.getSellPrice()) > 0) {
                sell(price);
            }
        }
    }
}
