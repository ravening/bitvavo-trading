package com.rakeshv.bitvavotrading.services;

import com.rakeshv.bitvavotrading.models.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

@Component("BTC")
@Slf4j
public class BtcCryptoTradeService extends CryptoTradeService {
    ReentrantLock lock = new ReentrantLock();
    ExecutorService service = Executors.newFixedThreadPool(2);
    List<Callable<Void>> threads = new ArrayList<>();
    BigDecimal currentPrice;
    @Autowired
    BitvavoTradeService bitvavoTradeService;

    @PostConstruct
    public void init() {
        threads.add(buy);
        threads.add(sell);
    }

    @Override
    public void checkBuySell(String price) {
        currentPrice = new BigDecimal(price);
        try {
            service.invokeAll(threads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Callable<Void> buy = () -> {
        List<Trade> buyList = bitvavoTradeService.getBuyList("BTC");
        if (buyList != null && !buyList.isEmpty()) {
            lock.lock();
            buyList
                    .forEach(trade -> {
                        if (currentPrice.compareTo(trade.getPrice()) < 0) {
                            if (trade.getNotify().equalsIgnoreCase("true")) {
                                buy(currentPrice.toString(), trade);
                                trade.setNotify("false");
                            }
                        }
                    });
            lock.unlock();
        }
        return null;
    };

    Callable<Void> sell = () -> {
        List<Trade> sellList = bitvavoTradeService.getSellList("BTC");
        if (sellList != null && !sellList.isEmpty()) {
            sellList.forEach(trade -> {
                if (currentPrice.compareTo(trade.getPrice()) > 0) {
                    if (trade.getNotify().equalsIgnoreCase("true")) {
                        sell(currentPrice.toString(), trade);
                        trade.setNotify("false");
                    }
                }
            });
        }
        return null;
    };

}
