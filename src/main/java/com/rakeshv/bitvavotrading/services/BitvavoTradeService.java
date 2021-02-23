package com.rakeshv.bitvavotrading.services;

import com.rakeshv.bitvavotrading.config.BitvavoTradeProperties;
import com.rakeshv.bitvavotrading.models.BuySellTuple;
import com.rakeshv.bitvavotrading.models.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class BitvavoTradeService {
    @Autowired
    BitvavoTradeProperties bitvavoTradeProperties;

    private Map<String, Trade> cryptoTrades;
    private Map<String, BuySellTuple> buySellTupleMap;

    @PostConstruct
    public void postConstruct() {
        cryptoTrades = new ConcurrentHashMap<>();
        buySellTupleMap = new ConcurrentHashMap<>();
        List<Trade> trades = bitvavoTradeProperties.getTrades();
        loadBuySellPrice(trades);
    }

    public void loadBuySellPrice(List<Trade> trades) {
        log.info("Getting new buy/sell prices for all trades");
        trades.forEach(trade -> {
            BuySellTuple buySellTuple;
            if (!buySellTupleMap.containsKey(trade.getCrypto())) {
                buySellTupleMap.put(trade.getCrypto(), new BuySellTuple());
            }
            buySellTuple = buySellTupleMap.get(trade.getCrypto());
            if (trade.getAction().equalsIgnoreCase("buy")) {
                buySellTuple.setBuyPrice(trade.getPrice());
            }
            if (trade.getAction().equalsIgnoreCase("sell")) {
                buySellTuple.setSellPrice(trade.getPrice());
            }
            cryptoTrades.put(trade.getCrypto(), trade);
            buySellTupleMap.put(trade.getCrypto(), buySellTuple);
        });
    }

    public BuySellTuple getBuySellTupleForCrypto(String crypto) {
        return buySellTupleMap.get(crypto);
    }
}
