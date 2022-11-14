package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Country;
import org.aquam.model.dto.CountryDto;
import org.aquam.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDto>> get() {
        return new ResponseEntity<>(countryService.read(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CountryDto> create(@RequestBody Country country) {
        return new ResponseEntity<>(countryService.create(country), HttpStatus.CREATED);
    }
}
