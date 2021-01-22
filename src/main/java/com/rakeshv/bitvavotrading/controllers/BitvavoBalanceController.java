package com.rakeshv.bitvavotrading.controllers;

import com.rakeshv.bitvavotrading.models.BitvavoBalance;
import com.rakeshv.bitvavotrading.services.BitvavoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/balance")
public class BitvavoBalanceController {
    @Autowired
    private BitvavoApiService bitvavoApiService;

    @GetMapping
    public ResponseEntity<List<BitvavoBalance>> getBalance() {
        return new ResponseEntity<>(bitvavoApiService.getBtcBalance(), HttpStatus.OK);
    }
}
