package org.aquam.service;

import org.aquam.model.AppUser;
import org.aquam.model.dto.AppUserModel;
import org.aquam.model.dto.SupportLetterDto;

import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> findUserByUsername(String username);
    AppUser findByUsername(String username);
    AppUser findById(Long id);
    AppUserModel readCurrent(String username);
    Boolean setCountry(String username, Long countryId);
    Boolean setLanguage(String username, Long languageId);
    Boolean saveLetter(SupportLetterDto supportLetterDto);
    String changeEmail(String email);
    public Boolean delete();
    Boolean block(String username);
}
