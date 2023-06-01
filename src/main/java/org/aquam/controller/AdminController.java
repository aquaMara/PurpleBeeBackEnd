package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.admin.SupportLetterModel;
import org.aquam.model.admin.UserModel;
import org.aquam.model.dto.SupportLetterDto;
import org.aquam.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<SupportLetterModel>> seeLetters() {
        return new ResponseEntity<>(adminService.getLetters(), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Boolean> sendAnswer(@PathVariable Long id, @RequestBody SupportLetterDto supportLetterDto) {
        return new ResponseEntity<>(adminService.answerLetter(id, supportLetterDto), HttpStatus.OK);
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<UserModel>> seeUsers() {
        return new ResponseEntity<>(adminService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/lock-user/{userId}")
    public ResponseEntity<Boolean> changeLockUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(adminService.changeLockUser(userId), HttpStatus.OK);
    }

}
