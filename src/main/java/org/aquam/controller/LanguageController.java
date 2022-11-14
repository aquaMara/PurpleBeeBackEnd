package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Language;
import org.aquam.model.dto.LanguageDto;
import org.aquam.service.LanguageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/language")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping
    public ResponseEntity<List<LanguageDto>> get() {
        return new ResponseEntity<>(languageService.read(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LanguageDto> create(@RequestBody Language language) {
        return new ResponseEntity<>(languageService.create(language), HttpStatus.CREATED);
    }
}
