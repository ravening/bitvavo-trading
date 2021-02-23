package com.rakeshv.bitvavotrading.listeners;

import com.rakeshv.bitvavotrading.config.BitvavoTradeProperties;
import com.rakeshv.bitvavotrading.models.Trade;
import com.rakeshv.bitvavotrading.services.BitvavoTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TradeListeners implements ApplicationListener<EnvironmentChangeEvent> {

    @Autowired
    BitvavoTradeProperties bitvavoTradeProperties;
    @Autowired
    BitvavoTradeService bitvavoTradeService;

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        List<Trade> trades = bitvavoTradeProperties.getTrades();
        bitvavoTradeService.loadBuySellPrice(trades);
    }
}
