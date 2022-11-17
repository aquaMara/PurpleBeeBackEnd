package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.dto.PaymentDto;
import org.aquam.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Boolean> getPattern(@RequestBody PaymentDto paymentDto) throws IOException {
        return new ResponseEntity<>(paymentService.getPattern(paymentDto), HttpStatus.OK);  // 200
    }
}
