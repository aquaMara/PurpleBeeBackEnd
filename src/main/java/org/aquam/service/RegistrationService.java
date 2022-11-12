package org.aquam.service;

import org.aquam.model.AppUser;
import org.aquam.model.request.ConfirmationRequest;
import org.aquam.model.request.RegistrationRequest;

import javax.validation.Valid;


public interface RegistrationService {

    String preRegister(RegistrationRequest registrationRequest);
    AppUser preSave(AppUser user);
    String sendConfirmationLetter(String username, String email);
    String generateConfirmationSequence(String username, String email);
    String register(String confirmationSequence);
    ConfirmationRequest decodeConfirmationSequence(String confirmationSequence);
    Boolean checkIfValid(AppUser user, String email);
    AppUser convertRequestToEntity(@Valid RegistrationRequest registrationRequest);
    void unconfirmedAccountsCleanup();
    Boolean usernameExists(String username);
}
