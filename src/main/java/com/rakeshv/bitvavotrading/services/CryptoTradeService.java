package com.rakeshv.bitvavotrading.services;

import com.rakeshv.bitvavotrading.models.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@Slf4j
public abstract class CryptoTradeService {
    @Autowired
    TelegramBotService telegramBotService;

    public abstract void checkBuySell(String price);

    void sell(String price, Trade trade) {
        log.warn("Selling crypto at {}", price);
        //TODO implement logic to buy/sell
        telegramBotService.sendNotification(trade.getCrypto().toUpperCase() + " price is €" + price);
    }

    void buy(String price, Trade trade) {
        log.warn("Buying crypto at {}", price);
        telegramBotService.sendNotification(trade.getCrypto().toUpperCase(Locale.ROOT) + " price is €" + price);
    }
}
