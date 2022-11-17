package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.dto.LiveRowDto;
import org.aquam.service.LiveRowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/live-row")
@RequiredArgsConstructor
public class LiveRowController {

    private final LiveRowService liveRowService;

    @PostMapping("/{patternId}")
    public ResponseEntity<List<LiveRowDto>> create(@PathVariable Long patternId, @RequestBody List<LiveRowDto> liveRowsDto) {
        return new ResponseEntity<>(liveRowService.create(liveRowsDto, patternId), HttpStatus.CREATED);
    }

    // when pay live rows become crossed rows and user always gets crossed rows, live rows only to save
    @GetMapping("/get/{patternId}")
    public ResponseEntity<List<LiveRowDto>> get(@PathVariable Long patternId, @RequestBody List<LiveRowDto> liveRowsDto) {
        return new ResponseEntity<>(liveRowService.create(liveRowsDto, patternId), HttpStatus.CREATED);
    }
}
