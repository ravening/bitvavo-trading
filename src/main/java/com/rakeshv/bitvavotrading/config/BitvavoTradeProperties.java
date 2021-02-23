package com.rakeshv.bitvavotrading.config;

import com.rakeshv.bitvavotrading.models.Trade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "bitvavo")
@AllArgsConstructor
@Builder
@Data
@RefreshScope
public class BitvavoTradeProperties {

    private final List<Trade> trades = new ArrayList<>();

    public List<Trade> getTrades() {
        return trades;
    }
}

