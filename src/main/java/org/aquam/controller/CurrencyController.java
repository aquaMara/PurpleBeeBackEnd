package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Currency;
import org.aquam.model.dto.CurrencyDto;
import org.aquam.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<CurrencyDto>> get() {
        return new ResponseEntity<>(currencyService.read(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CurrencyDto> create(@RequestBody Currency currency) {
        return new ResponseEntity<>(currencyService.create(currency), HttpStatus.CREATED);
    }
}
