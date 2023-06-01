package org.aquam.service.impl;

import org.aquam.model.AppUser;
import org.aquam.model.AppUserRole;
import org.aquam.model.SupportLetter;
import org.aquam.model.dto.SupportLetterDto;
import org.aquam.repository.AppUserRepository;
import org.aquam.repository.SupportLetterRepository;
import org.aquam.service.CountryService;
import org.aquam.service.LanguageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    @Mock
    private AppUserRepository userRepository;
    @Mock
    private CountryService countryService;
    @Mock
    private LanguageService languageService;
    @Mock
    private SupportLetterRepository supportLetterRepository;
    @InjectMocks
    private AppUserServiceImpl appUserService;

    @Test
    void saveLetter() {
        // given
        SupportLetter saved = createSupportLetter();
        saved.setId(33L);
        given(userRepository.findByUsername("aquam")).willReturn(Optional.of(createAppUser()));
        given(supportLetterRepository.save(createSupportLetter())).willReturn(saved);
        // when
        appUserService.saveLetter(createSupportLetterDto());
        // then
        then(supportLetterRepository).should().save(createSupportLetter());
    }

    public AppUser createAppUser() {
        AppUser user = new AppUser("aquam", "cheeseCake16!", "aquam@gmail.com");
        user.setLocked(false);
        user.setEnabled(true);
        user.setAppUserRole(AppUserRole.USER);
        user.setRegistrationDate(LocalDateTime.now());
        user.setId(6L);
        return user;
    }

    public SupportLetter createSupportLetter() {
        SupportLetter supportLetter = new SupportLetter();
        supportLetter.setBody("I have a problem with uploading a pattern");
        return supportLetter;
    }

    public SupportLetterDto createSupportLetterDto() {
        SupportLetterDto supportLetterDto = new SupportLetterDto();
        supportLetterDto.setBody("I have a problem with uploading a pattern");
        return supportLetterDto;
    }
}