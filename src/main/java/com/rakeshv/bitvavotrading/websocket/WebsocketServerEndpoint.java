package com.rakeshv.bitvavotrading.websocket;

import com.bitvavo.api.Bitvavo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakeshv.bitvavotrading.models.BitvavoBtcPrice;
import com.rakeshv.bitvavotrading.services.BitvavoApiService;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint(value = "/btc")
@Slf4j
public class WebsocketServerEndpoint {
    private ObjectMapper mapper = new ObjectMapper();
    private static Set<Session> allSessions;
    static ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

    @OnOpen
    public void onOpen(Session session) {
        log.info("Connected, sessionID = " + session.getId());
        allSessions = session.getOpenSessions();
        while (session.getOpenSessions().size() > 0) {
            timer.scheduleAtFixedRate(() -> sendTimeToClient(allSessions), 0, 1, TimeUnit.SECONDS);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("Received: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session " + session.getId() +
                " closed because " + closeReason);
    }

    private void sendTimeToClient(Set<Session> allSessions) {
        Bitvavo.Websocket websocket = BitvavoApiService.getWebsocket();
        websocket.subscriptionTicker("BTC-EUR", jsonObject -> {
            allSessions.forEach(s -> {
                try {
                    BitvavoBtcPrice btcPrice = mapper.readValue(jsonObject.toString(), BitvavoBtcPrice.class);
                    s.getBasicRemote().sendText(btcPrice.toString());
                } catch (Exception e) {
                    try {
                        s.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    log.error("Exception {}", e.getMessage());
                }
            });
        });
    }
}
