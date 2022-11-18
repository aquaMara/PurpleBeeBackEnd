package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.LiveRow;
import org.aquam.model.dto.LiveRowDto;
import org.aquam.model.dto.PatternDto;
import org.aquam.model.dto.PatternModel;
import org.aquam.service.PatternService;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pattern")
@RequiredArgsConstructor
public class PatternController {

    private final PatternService patternService;

    // a user creates a pattern
    @PostMapping("/{username}")
    public ResponseEntity<Long> create(@PathVariable String username, @RequestBody PatternDto patternDto) {
        return new ResponseEntity<>(patternService.create(patternDto, username), HttpStatus.OK);
    }

    // get all patterns
    @GetMapping("/all")
    public ResponseEntity<List<PatternDto>> get() {
        return new ResponseEntity<>(patternService.read(), HttpStatus.OK);
    }

    @GetMapping("/all/{name}")
    public ResponseEntity<List<PatternDto>> getByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(patternService.searchByName(name), HttpStatus.OK);
    }

    // get one pattern by id
    @GetMapping("/{patternId}")
    public ResponseEntity<PatternModel> getOne(@PathVariable Long patternId) {
        return new ResponseEntity<>(patternService.readPatternModel(patternId), HttpStatus.OK);
    }

}

// @RequestParam(value = "data") MultipartFile file
// @PreAuthorize("hasRole('ADMIN')")
// , @RequestBody ArrayList<LiveRow> liveRows
    /*
    @PostMapping("/upload")
    public ResponseEntity<String> addFile(@RequestParam(value = "data") MultipartFile file) throws IOException {
        return new ResponseEntity<>("subjectService.addFile(subjectId, file)", HttpStatus.OK);  // 200
    }
     */