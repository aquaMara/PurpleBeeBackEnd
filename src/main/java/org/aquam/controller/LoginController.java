package org.aquam.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.configuration.authentication.UserAuthenticationProvider;
import org.aquam.configuration.token.TokenService;
import org.aquam.model.AppUser;
import org.aquam.model.request.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/signin")
@RequiredArgsConstructor
public class LoginController {

    private final UserAuthenticationProvider authenticationProvider;
    private final TokenService tokenService;

    @PostMapping("")
    public ResponseEntity<Map<Object, Object>> login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticatedUser = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        AppUser principal = (AppUser) authenticatedUser.getPrincipal();
        String token = tokenService.createToken(principal.getUsername(), principal.getAppUserRole());
        return new ResponseEntity<>(createResponse(principal, token), HttpStatus.OK);
    }

    private Map<Object, Object> createResponse(AppUser principal, String token) {
        Map<Object, Object> response = new HashMap<>();
        response.put("username", principal.getUsername());
        response.put("token", token);
        response.put("role", principal.getAppUserRole());
        return response;
    }
}
