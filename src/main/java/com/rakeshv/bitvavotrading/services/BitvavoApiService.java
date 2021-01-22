package com.rakeshv.bitvavotrading.services;

import com.bitvavo.api.Bitvavo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshv.bitvavotrading.models.BitvavoApi;
import com.rakeshv.bitvavotrading.models.BitvavoTickerFilter;
import com.rakeshv.bitvavotrading.models.BitvavoTickerPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Slf4j
public class BitvavoApiService {

    private Bitvavo bitvavo;
    private ObjectMapper mapper;

    @Value("${bitvavo.apikey}")
    private String bitvavoApiKey;

    @Value("${bitvavo.secretkey}")
    private String bitvavoSecretKey;

    @Value("${bitvavo.resturl}")
    private String restUrl;

    @Value("${bitvavo.wsurl")
    private String wsUrl;

    @PostConstruct
    private synchronized void initBitvavoApiService() {
        mapper = new ObjectMapper();

        BitvavoApi bitvavoApi = BitvavoApi.builder()
                .apiKey(bitvavoApiKey)
                .secretKey(bitvavoSecretKey)
                .restUrl(restUrl)
                .wsUrl(wsUrl)
                .accessWindow(10000)
                .debugging(false).build();

        if (this.bitvavo == null)
            this.bitvavo = new Bitvavo(new JSONObject(bitvavoApi.toString()));

        LocalDateTime localDateTime = Instant.ofEpochMilli(this.bitvavo.time().getLong("time"))
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        log.info("Bitvavo server time is {}", localDateTime.toString());
    }

    public BitvavoTickerPrice getTickerPrice(String ticker) {
        BitvavoTickerFilter filter = BitvavoTickerFilter.builder()
                .market(ticker).build();

        return getTickerPrice(filter);
    }

    private BitvavoTickerPrice getTickerPrice(BitvavoTickerFilter filter) {
        JSONArray response = bitvavo.tickerPrice(new JSONObject(filter.toString()));

        for (int i = 0; i < response.length(); i++) {
            JSONObject jsonObject = response.getJSONObject(i);
            if (jsonObject != null && jsonObject.get("market").equals(filter.getMarket())) {
                try {
                    return mapper.readValue(jsonObject.toString(), BitvavoTickerPrice.class);
                } catch (Exception e) {
                    return BitvavoTickerPrice.builder().build();
                }
            }
        }

        return BitvavoTickerPrice.builder().build();
    }
}
