package com.rakeshv.bitvavoconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BitvavoConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BitvavoConfigServerApplication.class, args);
    }

}
