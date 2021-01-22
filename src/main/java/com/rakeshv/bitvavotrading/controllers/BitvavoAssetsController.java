package com.rakeshv.bitvavotrading.controllers;

import com.rakeshv.bitvavotrading.models.BitvavoAsset;
import com.rakeshv.bitvavotrading.services.BitvavoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/assets")
public class BitvavoAssetsController {
    @Autowired
    BitvavoApiService apiService;

    @GetMapping
    public ResponseEntity<List<BitvavoAsset>> getAssets() {
        return new ResponseEntity<>(apiService.getBitvavoAssets(), HttpStatus.OK);
    }
}
