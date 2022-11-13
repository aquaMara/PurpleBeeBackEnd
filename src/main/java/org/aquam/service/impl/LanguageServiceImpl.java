package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Language;
import org.aquam.model.dto.LanguageDto;
import org.aquam.repository.LanguageRepository;
import org.aquam.service.LanguageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final ModelMapper modelMapper;

    @Override
    public LanguageDto findByName(String name) {
        return languageRepository.findByName(name).map(this::mapToDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Language with name "+ name + " does not exist"));
    }

    @Override
    public Boolean exists(String name) {
        return languageRepository.findByName(name).isPresent();
    }

    @Override
    public List<LanguageDto> readAll() {
        List<Language> languages = languageRepository.findAll();
        if (languages.isEmpty())
            throw new EntityExistsException("No languages");
        return languages.stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LanguageDto create(Language language) {
        if (exists(language.getName()))
            throw new EntityExistsException("Language with name " + language.getName() + " exists");
        Language saved = languageRepository.save(language);
        return mapToDto(saved);
    }

    @Override
    public LanguageDto mapToDto(Language language) {
        return modelMapper.map(language, LanguageDto.class);
    }

    @Override
    public Language mapFromDto(LanguageDto languageDto) {
        return modelMapper.map(languageDto, Language.class);
    }
}
