package org.aquam.service;

import org.aquam.model.Language;
import org.aquam.model.dto.LanguageDto;

import java.util.List;

public interface LanguageService {

    LanguageDto findByName(String name);
    Boolean exists(String name);
    List<LanguageDto> readAll();
    LanguageDto create(Language language);
    LanguageDto mapToDto(Language language);
    Language mapFromDto(LanguageDto languageDto);
}
