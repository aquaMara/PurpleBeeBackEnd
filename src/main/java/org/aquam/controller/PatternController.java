package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.LiveRow;
import org.aquam.model.dto.LiveRowDto;
import org.aquam.model.dto.PatternDto;
import org.aquam.model.dto.PatternModel;
import org.aquam.service.PatternService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/all/{search}")
    public ResponseEntity<List<PatternDto>> getByName(@PathVariable("search") String name) {
        return new ResponseEntity<>(patternService.searchByName(name), HttpStatus.OK);
    }

    @GetMapping("/all/craft/{craftId}")
    public ResponseEntity<List<PatternDto>> filter(@PathVariable("craftId") Long craftId) {
        return new ResponseEntity<>(patternService.filterByCraft(craftId), HttpStatus.OK);
    }

    // get one pattern by id
    @GetMapping("/{patternId}")
    public ResponseEntity<PatternModel> getOne(@PathVariable Long patternId) {
        return new ResponseEntity<>(patternService.readPatternModel(patternId), HttpStatus.OK);
    }

    @PostMapping("/upload/{patternId}")
    public ResponseEntity<Boolean> uploadImage(@PathVariable("patternId") Long patternId,
                                               @RequestParam("file") MultipartFile multipartFile) {
        return new ResponseEntity<>(patternService.uploadImage(patternId, multipartFile), HttpStatus.CREATED);
    }

    @GetMapping("/image/{patternId}")
    public ResponseEntity<byte []> getImage(@PathVariable("patternId") Long patternId) {
        byte[] image = patternService.getImage(patternId);
        return new ResponseEntity<>(patternService.getImage(patternId), HttpStatus.CREATED);
    }

    @PutMapping("/admin/{patternId}")
    public ResponseEntity<Boolean> lock(@PathVariable("patternId") Long patternId) {
        return new ResponseEntity<>(patternService.lock(patternId), HttpStatus.OK);
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