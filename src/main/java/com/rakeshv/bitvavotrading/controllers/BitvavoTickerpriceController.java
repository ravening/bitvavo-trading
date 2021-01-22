package com.rakeshv.bitvavotrading.controllers;

import com.rakeshv.bitvavotrading.models.BitvavoTickerPrice;
import com.rakeshv.bitvavotrading.services.BitvavoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ticker/")
public class BitvavoTickerpriceController {
    @Autowired
    BitvavoApiService bitvavoApiService;

    @GetMapping("/{name}")
    public ResponseEntity<BitvavoTickerPrice> getTickerPrice(@PathVariable("name") String name) {
        return new ResponseEntity<>(bitvavoApiService.getTickerPrice(name), HttpStatus.OK);
    }
}
