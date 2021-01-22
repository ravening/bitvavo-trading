package com.rakeshv.bitvavotrading.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BitvavoApi {
    private String apiKey;
    private String secretKey;
    private String restUrl;
    private String wsUrl;
    private int accessWindow;
    private boolean debugging;

    @Override
    public String toString() {
        return "{" +
                "APIKEY: '" + apiKey + '\'' +
                ", APISECRET: '" + secretKey + '\'' +
                ", RESTURL: '" + restUrl + '\'' +
                ", WSURL: '" + wsUrl + '\'' +
                ", ACCESSWINDOW: " + accessWindow +
                ", DEBUGGING: " + debugging +
                '}';
    }
}
