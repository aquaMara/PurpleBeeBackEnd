package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.dto.CommentDto;
import org.aquam.service.PatternService;
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
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final PatternService patternService;

    @GetMapping("/{patternId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable("patternId") Long patternId) {
        return new ResponseEntity<>(patternService.getComments(patternId), HttpStatus.OK);
    }

    @PostMapping("/{patternId}")
    public ResponseEntity<String> setComment(@PathVariable("patternId") Long patternId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(patternService.setComment(patternId, commentDto), HttpStatus.OK);
    }
}
