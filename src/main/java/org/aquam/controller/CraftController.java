package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Craft;
import org.aquam.service.CraftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/craft")
@RequiredArgsConstructor
public class CraftController {

    private final CraftService craftService;

    @GetMapping
    public ResponseEntity<List<Craft>> read() {
        return new ResponseEntity<>(craftService.readAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Craft> create(@RequestBody Craft craft) {
        return new ResponseEntity<>(craftService.create(craft), HttpStatus.CREATED);
    }
}
