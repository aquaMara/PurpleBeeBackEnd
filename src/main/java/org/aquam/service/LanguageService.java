package org.aquam.service;

import org.aquam.model.Language;
import org.aquam.model.dto.LanguageDto;

import java.util.List;

public interface LanguageService {

    LanguageDto findByName(String name);
    Language findById(Long id);
    Boolean exists(String name);
    List<LanguageDto> read();
    LanguageDto create(Language language);
    LanguageDto mapToDto(Language language);
    Language mapFromDto(LanguageDto languageDto);
}
