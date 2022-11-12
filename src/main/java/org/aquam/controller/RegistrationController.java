package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.model.request.RegistrationRequest;
import org.aquam.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/check")
    public ResponseEntity<Boolean> checkUsername(@RequestBody String username) throws InterruptedException {
        return new ResponseEntity<>(registrationService.usernameExists(username.replace("=", "")), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) throws InterruptedException {
        return new ResponseEntity<>(registrationService.preRegister(registrationRequest), HttpStatus.CREATED);
    }

    @GetMapping("/confirm/{confirmationSequence}")
    public ResponseEntity<String> confirm(@PathVariable("confirmationSequence")String confirmationSequence) {
        return new ResponseEntity<>(registrationService.register(confirmationSequence), HttpStatus.OK);
    }
}
