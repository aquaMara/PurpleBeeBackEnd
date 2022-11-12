package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.exception.RegistrationTimeoutException;
import org.aquam.model.AppUser;
import org.aquam.model.AppUserRole;
import org.aquam.model.request.ConfirmationRequest;
import org.aquam.model.request.RegistrationRequest;
import org.aquam.repository.AppUserRepository;
import org.aquam.service.EmailService;
import org.aquam.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Set;
import java.util.concurrent.TimeoutException;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final AppUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;
    private final ModelMapper modelMapper;
    private final Validator validator;
    private final AppUserServiceImpl userService;

    @Value("${purple-bee.url}")
    private String purpleBeeUrl;

    @Value("${purple-bee.confirmation.expiration.in-hours}")
    private Long purpleBeeConfirmationExpiration;

    @Override
    public String preRegister(RegistrationRequest registrationRequest) {
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent())
            throw new EntityExistsException("Username " +  registrationRequest.getUsername() + " is taken");
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent())
            throw new EntityExistsException("Email " + registrationRequest.getEmail() + " is taken");
        AppUser user = convertRequestToEntity(registrationRequest);
        AppUser userFromRepository = preSave(user);
        return sendConfirmationLetter(userFromRepository.getUsername(), userFromRepository.getEmail());
    }

    @Override
    public AppUser preSave(AppUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAppUserRole(AppUserRole.USER);
        user.setRegistrationDate(LocalDateTime.now());
        user.setLocked(false);
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public String sendConfirmationLetter(String username, String email) {
        String confirmationSequence = generateConfirmationSequence(username, email);
        String confirmationLink = purpleBeeUrl + "signup/confirm/" + confirmationSequence;
        return emailService.sendEmail(new ConfirmationRequest(username, email, confirmationLink));
    }

    @Override
    public String generateConfirmationSequence(String username, String email) {
        String data = username + "///" + email;
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    @Override
    public String register(String confirmationSequence) {
        ConfirmationRequest confirmationRequest = decodeConfirmationSequence(confirmationSequence);
        if (userRepository.findByUsername(confirmationRequest.getUsername()).isEmpty())
            throw new EntityNotFoundException("Confirmation link error, try to re-register");
        AppUser user = userRepository.findByUsername(confirmationRequest.getUsername()).get();
        checkIfValid(user, confirmationRequest.getEmail());
        user.setEnabled(true);
        user.setRegistrationDate(LocalDateTime.now());
        userRepository.save(user);
        return "Successfully registered";
    }

    @Override
    public ConfirmationRequest decodeConfirmationSequence(String confirmationSequence) {
        byte[] decodedSequence = Base64.getDecoder().decode(confirmationSequence);
        String decodedData = new String(decodedSequence);
        String[] splittedData = decodedData.split("///");
        String username = splittedData[0];
        String email = splittedData[1];
        return new ConfirmationRequest(username, email);
    }

    @Override
    public Boolean checkIfValid(AppUser user, String email) {
        if (!user.getEmail().equals(email))
            throw new EntityNotFoundException("Confirmation link error, need to re-register");
        if (user.getEnabled()) {
            throw new EntityExistsException("Account already enabled");
        } else if (user.getRegistrationDate().isBefore(
                LocalDateTime.now().minusHours(purpleBeeConfirmationExpiration))) {
            userRepository.deleteById(user.getId());
            throw new RegistrationTimeoutException("Link is expired");
        }
        return true;
    }

    @Override
    public AppUser convertRequestToEntity(@Valid RegistrationRequest registrationRequest) {
        Set<ConstraintViolation<RegistrationRequest>> validationExceptions = validator.validate(registrationRequest);
        if (!validationExceptions.isEmpty())
            throw new ConstraintViolationException(validationExceptions);
        AppUser user = modelMapper.map(registrationRequest, AppUser.class);
        return user;
    }

    @Scheduled(cron = "@weekly")
    @Override
    public void unconfirmedAccountsCleanup() {
        LocalDateTime expirationThreshold = LocalDateTime.now().minusHours(purpleBeeConfirmationExpiration);
        userRepository.deleteUnconfirmedAccounts(false, expirationThreshold);
    }

    @Override
    public Boolean usernameExists(String username) {
        System.out.println(username);
        if (userRepository.findByUsername(username).isEmpty())
            return false;
        return true;
    }
}