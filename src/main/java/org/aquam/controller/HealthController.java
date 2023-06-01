package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purple-bee/health")
@RequiredArgsConstructor
public class HealthController {

    @GetMapping
    public ResponseEntity<String> checkHealth() {
        return new ResponseEntity<>("Hello from PurpleBee", HttpStatus.OK);
    }
}
