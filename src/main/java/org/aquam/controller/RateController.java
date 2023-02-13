package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.dto.RateDto;
import org.aquam.service.PatternService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rate")
@RequiredArgsConstructor
public class RateController {

    private final PatternService patternService;

    @GetMapping("/{patternId}/value/{rating}")
    public ResponseEntity<Double> setRate(@PathVariable("patternId") Long patternId, @PathVariable("rating") Double value) {
        return new ResponseEntity<>(patternService.setRate(patternId, value), HttpStatus.OK);
    }
}
