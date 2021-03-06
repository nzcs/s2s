package com.nzcs.s2s.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private Producer producer;


    @GetMapping("/api/ping")
    public ResponseEntity<String> ping(@RequestHeader String client) {
        return producer.getHello(client);
    }
}
