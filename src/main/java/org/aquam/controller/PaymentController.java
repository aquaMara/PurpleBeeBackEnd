package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.dto.PatternDto;
import org.aquam.model.dto.PaymentDto;
import org.aquam.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /** User pays for a pattern */
    @PostMapping
    public ResponseEntity<Boolean> getPattern(@RequestBody PaymentDto paymentDto) throws IOException {
        return new ResponseEntity<>(paymentService.payForThePattern(paymentDto), HttpStatus.CREATED);  // 200
    }

    /** User gets her/his patterns by username */
    @GetMapping("/{username}")
    public ResponseEntity<List<PatternDto>> get(@PathVariable String username) {
        return new ResponseEntity<>(paymentService.getPatternsByUsername(username), HttpStatus.OK);
    }
}
