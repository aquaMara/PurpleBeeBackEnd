package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.dto.AppUserModel;
import org.aquam.model.dto.SupportLetterDto;
import org.aquam.service.AppUserService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/current/{username}")
    public ResponseEntity<AppUserModel> getCurrent(@PathVariable("username") String username) {
        return new ResponseEntity<>(appUserService.readCurrent(username), HttpStatus.OK);
    }

    @PutMapping("/{username}/country/{countryId}")
    public ResponseEntity<Boolean> setCountry(@PathVariable("username") String username, @PathVariable("countryId") Long countryId) {
        return new ResponseEntity<>(appUserService.setCountry(username, countryId), HttpStatus.CREATED);
    }

    @PutMapping("/{username}/language/{languageId}")
    public ResponseEntity<Boolean> setLanguage(@PathVariable("username") String username, @PathVariable("languageId") Long languageId) {
        return new ResponseEntity<>(appUserService.setLanguage(username, languageId), HttpStatus.CREATED);
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<String> setEmail(@PathVariable("email")String email) {
        return new ResponseEntity<>(appUserService.changeEmail(email), HttpStatus.OK);
    }

    @PostMapping("/support/{username}")
    public ResponseEntity<Boolean> saveLetter(@PathVariable String username, @RequestBody SupportLetterDto supportLetterDto) {
        return new ResponseEntity<>(appUserService.saveLetter(supportLetterDto), HttpStatus.CREATED);
    }

    @PutMapping("/delete")
    public ResponseEntity<Boolean> delete() {
        return new ResponseEntity<>(appUserService.delete(), HttpStatus.OK);
    }

    @PutMapping("/admin/{username}")
    public ResponseEntity<Boolean> lock(@PathVariable String username) {
        return new ResponseEntity<>(appUserService.block(username), HttpStatus.OK);
    }
}
