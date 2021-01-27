package com.rakeshv.bitvavotrading.websocket;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.tyrus.server.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
@Component
public class WebsocketServer implements CommandLineRunner {

    public void run(String... args) throws Exception {
        Server server = new Server("localhost", 8026, "/websockets", null, WebsocketServerEndpoint.class);
        try {
            server.start();
            log.info("Websocket server is running");
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            bufferRead.readLine();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            server.stop();
        }

    }
}
