package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.AppUser;
import org.aquam.model.AppUserSettings;
import org.aquam.model.Country;
import org.aquam.model.Language;
import org.aquam.model.SupportLetter;
import org.aquam.model.dto.AppUserDto;
import org.aquam.model.dto.AppUserModel;
import org.aquam.model.dto.SupportLetterDto;
import org.aquam.model.request.RegistrationRequest;
import org.aquam.repository.AppUserRepository;
import org.aquam.repository.SupportLetterRepository;
import org.aquam.service.AppUserService;
import org.aquam.service.CountryService;
import org.aquam.service.LanguageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    private final AppUserRepository userRepository;
    private final CountryService countryService;
    private final LanguageService languageService;
    private final SupportLetterRepository supportLetterRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Username: %d not found", username)));
    }

    @Override
    public Optional<AppUser> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public AppUser findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public AppUserModel readCurrent(String username) {
        AppUser current = findByUsername(username);
        AppUserSettings appUserSettings = current.getAppUserSettings();
        AppUserModel model = new AppUserModel(
                username,
                current.getEmail(),
                current.getInstagram(),
                current.getRegistrationDate()
        );
        return model;
    }

    @Override
    public Boolean setCountry(String username, Long countryId) {
        AppUser current = findByUsername(username);
        Country country = countryService.findById(countryId);
        current.getAppUserSettings().setCountry(country);
        return true;
    }

    @Override
    public Boolean setLanguage(String username, Long languageId) {
        AppUser current = findByUsername(username);
        Language language = languageService.findById(languageId);
        current.getAppUserSettings().setLanguage(language);
        return true;
    }

    @Override
    public Boolean saveLetter(SupportLetterDto supportLetterDto) {
        AppUser user = findByUsername(getAuthentication().getName());
        SupportLetter supportLetter = new SupportLetter();
        supportLetter.setEmail(user.getEmail());
        supportLetter.setTitle(supportLetterDto.getTitle());
        supportLetter.setBody(supportLetterDto.getBody());
        SupportLetter save = supportLetterRepository.save(supportLetter);
        return true;
    }

    @Override
    public String changeEmail(String email) {
        Authentication authentication = getAuthentication();
        String username = authentication.getName();
        AppUser appUser = findByUsername(username);
        appUser.setEmail(email);
        AppUser save = userRepository.save(appUser);
        return appUser.getEmail();
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    public Boolean delete() {
        Authentication authentication = getAuthentication();
        AppUser appUser = findByUsername(authentication.getName());
        appUser.setEmail(null);
        appUser.setPassword(null);
        userRepository.save(appUser);
        return true;
    }

    @Override
    public Boolean block(String username) {
        AppUser appUser = findByUsername(username);
        appUser.setLocked(true);
        userRepository.save(appUser);
        return true;
    }
}
