package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.dto.LiveRowDto;
import org.aquam.model.dto.LiveRowModel;
import org.aquam.model.dto.PatternModel;
import org.aquam.model.dto.PatternUser;
import org.aquam.service.LiveRowService;
import org.aquam.service.LiveRowStateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/live-row")
@RequiredArgsConstructor
public class LiveRowController {

    private final LiveRowService liveRowService;
    private final LiveRowStateService liveRowStateService;

    // a user creates live pattern
    @PostMapping("/{patternId}")
    public ResponseEntity<List<LiveRowDto>> create(@PathVariable Long patternId, @RequestBody List<LiveRowDto> liveRowsDto) {
        return new ResponseEntity<>(liveRowService.create(liveRowsDto, patternId), HttpStatus.CREATED);
    }

    // get live pattern
    // userId, patternId
    @GetMapping("/{patternId}/user/{username}")
    public ResponseEntity<List<LiveRowModel>> getLivePattern(@PathVariable("patternId") Long patternId, @PathVariable("username") String username) {
        return new ResponseEntity<>(liveRowStateService.readLivePattern(patternId, username), HttpStatus.OK);
    }

    // update row in live pattern
    @PutMapping("/{liveRowId}/user/{username}")
    public ResponseEntity<Boolean> updateLivePatternRow(@PathVariable("liveRowId") Long liveRowId, @PathVariable("username") String username) {
        return new ResponseEntity<>(liveRowStateService.updateLivePatternRow(liveRowId, username), HttpStatus.CREATED);
    }
}
