package com.rakeshv.bitvavotrading.services;

import com.bitvavo.api.Bitvavo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshv.bitvavotrading.models.BitvavoApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
