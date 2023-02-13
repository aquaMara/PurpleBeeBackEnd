package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.admin.SupportLetterModel;
import org.aquam.model.admin.UserModel;
import org.aquam.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /*
    @PostMapping("/support")
    public ResponseEntity<Boolean> sendAnswer() {
        return new ResponseEntity<>(adminService.sendAnswer(), HttpStatus.OK);
    }
     */

    @GetMapping("/get-letters")
    public ResponseEntity<List<SupportLetterModel>> seeLetters() {
        return new ResponseEntity<>(adminService.getLetters(), HttpStatus.OK);
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
