package com.rakeshv.bitvavotrading.listeners;

import com.rakeshv.bitvavotrading.models.PriceEvent;
import com.rakeshv.bitvavotrading.services.CryptoTradeService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PriceEventListener {

    private final Map<String, CryptoTradeService> cryptoTradeServiceMap;

    PriceEventListener(Map<String, CryptoTradeService> map) {
        this.cryptoTradeServiceMap = map;
    }

    @Async
    @EventListener
    public void handlePriceEvent(PriceEvent priceEvent) {
        String crypto = priceEvent.getName();
        if (priceEvent.getPrice() != null)
            this.cryptoTradeServiceMap.get(crypto).checkBuySell(priceEvent.getPrice());
    }
}
