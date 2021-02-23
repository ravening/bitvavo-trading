package com.rakeshv.bitvavotrading.controllers;

import com.rakeshv.bitvavotrading.config.BitvavoTradeProperties;
import com.rakeshv.bitvavotrading.models.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class BitvavoActiveTradesController {
    @Autowired
    BitvavoTradeProperties bitvavoTradeProperties;

    @GetMapping
    public List<Trade> getActiveTrades() {
        return bitvavoTradeProperties.getTrades();
    }
}
