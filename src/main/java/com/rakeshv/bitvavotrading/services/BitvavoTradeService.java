package com.rakeshv.bitvavotrading.services;

import com.rakeshv.bitvavotrading.config.BitvavoTradeProperties;
import com.rakeshv.bitvavotrading.models.Trade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class BitvavoTradeService {
    @Autowired
    BitvavoTradeProperties bitvavoTradeProperties;

    private Map<String, BuySellList> buySellListMap;

    @PostConstruct
    public void postConstruct() {
        buySellListMap = new ConcurrentHashMap<>();
        List<Trade> trades = bitvavoTradeProperties.getTrades();
        loadBuySellPrice(trades);
    }

    public void loadBuySellPrice(List<Trade> trades) {
        log.info("Getting new buy/sell prices for all trades");
        buySellListMap.keySet().forEach(trade -> {
            buySellListMap.get(trade).getBuyList().clear();
            buySellListMap.get(trade).getSellList().clear();
        });
        trades.forEach(trade -> {
            log.info("Trade is {},", trade);
            if (trade.getAction().equalsIgnoreCase("buy")) {
                addToBuyList(trade.getCrypto(), trade);
            } else if (trade.getAction().equalsIgnoreCase("sell")) {
                addToSellList(trade.getCrypto(), trade);
            } else {
                log.error("Invalid trade : {}", trade);
            }
        });
    }

    private void addToBuyList(String crypto, Trade trade) {
        if (!buySellListMap.containsKey(crypto)) {
            buySellListMap.put(crypto, BuySellList.builder().build());
        }
        if (buySellListMap.get(crypto).buyList == null) {
            buySellListMap.get(crypto).buyList = new ArrayList<>();
        }
        buySellListMap.get(crypto).getBuyList().add(trade);
    }

    private void addToSellList(String crypto, Trade trade) {
        if (!buySellListMap.containsKey(crypto)) {
            buySellListMap.put(crypto, BuySellList.builder().build());
        }
        if (buySellListMap.get(crypto).getSellList() == null) {
            buySellListMap.get(crypto).sellList = new ArrayList<>();
        }
        buySellListMap.get(crypto).getSellList().add(trade);
    }

    public List<Trade> getBuyList(String crypto) {
        if (this.buySellListMap.containsKey(crypto))
            return this.buySellListMap.get(crypto).getBuyList();

        return null;
    }

    public List<Trade> getSellList(String crypto) {
        if (this.buySellListMap.containsKey(crypto))
            return this.buySellListMap.get(crypto).getSellList();

        return null;
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class BuySellList {
    List<Trade> buyList;
    List<Trade> sellList;
}
