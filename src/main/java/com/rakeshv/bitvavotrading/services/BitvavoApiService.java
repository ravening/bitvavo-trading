package com.rakeshv.bitvavotrading.services;

import com.bitvavo.api.Bitvavo;
import com.bitvavo.api.WebsocketClientEndpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshv.bitvavotrading.models.BitvavoApi;
import com.rakeshv.bitvavotrading.models.BitvavoAsset;
import com.rakeshv.bitvavotrading.models.BitvavoBalance;
import com.rakeshv.bitvavotrading.models.BitvavoBtcPrice;
import com.rakeshv.bitvavotrading.models.BitvavoTickerFilter;
import com.rakeshv.bitvavotrading.models.BitvavoTickerPrice;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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

    @Value("${bitvavo.wsurl}")
    private String wsUrl;

    private static String btcPrice;

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

        Bitvavo.Websocket ws = this.bitvavo.newWebsocket();

        ws.setErrorCallback(new WebsocketClientEndpoint.MessageHandler() {
            public void handleMessage(JSONObject response) {
                System.out.println("Found ERROR, own callback." + response);
            }
        });

        ws.time(new WebsocketClientEndpoint.MessageHandler() {
            public void handleMessage(JSONObject responseObject) {
                System.out.println(responseObject.getJSONObject("response").toString(2));
            }
        });
        ws.subscriptionTicker("BTC-EUR", jsonObject -> {
            BitvavoBtcPrice bitvavoBtcPrice = null;
            try {
                bitvavoBtcPrice = mapper.readValue(jsonObject.toString(), BitvavoBtcPrice.class);
                if (bitvavoBtcPrice != null) {
                    btcPrice = bitvavoBtcPrice.getLastPrice();
                    log.info("{}", bitvavoBtcPrice.toString());
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

//        ws.subscriptionTrades("BTC-EUR", new WebsocketClientEndpoint.MessageHandler() {
//           public void handleMessage(JSONObject responseObject) {
//               log.info("{}", responseObject.toString());
//           }
//        });
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

    public List<BitvavoAsset> getBitvavoAssets() {
        List<BitvavoAsset> assetList = new ArrayList<>();
        JSONArray response = bitvavo.assets(new JSONObject());
        response.forEach(asset -> {
            BitvavoAsset bitvavoAsset = null;
            try {
                bitvavoAsset = mapper.readValue(asset.toString(), BitvavoAsset.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            assetList.add(bitvavoAsset);
        });

        return assetList;
    }

    public List<BitvavoBalance> getBtcBalance() {
        List<BitvavoBalance> balanceList = new ArrayList<>();
        JSONArray response = bitvavo.balance(new JSONObject());
        for (int i = 0; i < response.length(); i++) {
            JSONObject jsonObject = response.getJSONObject(i);
            try {
                if (jsonObject != null) {
                    balanceList.add( mapper.readValue(jsonObject.toString(), BitvavoBalance.class));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return balanceList;
    }

    public static String getBtcPrice() {
        return btcPrice;
    }
}
