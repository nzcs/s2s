package com.nzcs.s2s.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "a", url = "https://localhost:8082")
public interface Producer {

    @GetMapping("/api/hello")
    ResponseEntity<String> getHello(@RequestHeader String client);
}
