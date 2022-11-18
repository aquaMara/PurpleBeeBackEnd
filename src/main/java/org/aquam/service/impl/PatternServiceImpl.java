package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Category;
import org.aquam.model.DifficultyLevel;
import org.aquam.model.Pattern;
import org.aquam.model.dto.PatternDto;
import org.aquam.model.dto.PatternModel;
import org.aquam.repository.PatternRepository;
import org.aquam.service.AppUserService;
import org.aquam.service.CategoryService;
import org.aquam.service.CraftService;
import org.aquam.service.CurrencyService;
import org.aquam.service.LanguageService;
import org.aquam.service.PatternService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.xml.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PatternServiceImpl implements PatternService {

    private final PatternRepository patternRepository;
    private final ModelMapper modelMapper;
    //private final Validator validator;

    private final AppUserService appUserService;
    private final CraftService craftService;
    private final CategoryService categoryService;
    private final LanguageService languageService;
    private final CurrencyService currencyService;

    @Override
    public Pattern findById(Long id) {
        return patternRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Pattern with id " + id + " does not exist"));
    }

    @Override
    public Boolean exists(Long id) {
        return patternRepository.findById(id).isPresent();
    }

    @Override
    public List<PatternDto> read() {
        List<Pattern> patterns = patternRepository.findAll();
        if (patterns.isEmpty())
            throw new EntityNotFoundException("No patterns");
        return patterns.stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatternDto> searchByName(String name) {
        List<Pattern> patterns = patternRepository.findAll();
        if (patterns.isEmpty())
            throw new EntityNotFoundException("No patterns");
        return patterns.stream()
                .filter(s -> s.getName().contains(name))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PatternDto readOne(Long id) {
        Pattern pattern = findById(id);
        return mapToDto(pattern);
    }

    @Override
    public List<PatternDto> readByIds(List<Long> idsOfPatterns) {
        return idsOfPatterns.stream()
                .map(this::readOne)
                .collect(Collectors.toList());
    }

    @Override
    public PatternModel readPatternModel(Long id) {
        Pattern pattern = findById(id);
        PatternModel patternModel = new PatternModel(
                pattern.getId(),
                pattern.getName(),
                pattern.getLittleDescription(),
                pattern.getPrice(),
                pattern.getDifficultyLevel(),
                pattern.getAvgRate(),
                pattern.getImagePath(),
                pattern.getPdfPath()
        );
        patternModel.setCreatorUsername(pattern.getCreator().getUsername());
        patternModel.setCraftName(pattern.getCraft().getName());
        patternModel.setCategoryName(pattern.getCategory().getName());
        patternModel.setLanguageName(pattern.getCategory().getName());
        patternModel.setCurrencyName(pattern.getCurrency().getName());
        return patternModel;
    }

    @Override
    public Long create(PatternDto patternDto, String username) {
        // todo: if user has pattern with the same name ?
        Pattern pattern = new Pattern(patternDto.getName(), patternDto.getLittleDescription(),
                patternDto.getDifficultyLevel(), patternDto.getPrice());
        pattern.setCreator(appUserService.findByUsername(username));
        pattern.setCraft(craftService.findById(patternDto.getCraftId()));
        pattern.setCategory(categoryService.findById(patternDto.getCategoryId()));
        pattern.setLanguage(languageService.findById(patternDto.getLanguageId()));
        pattern.setCurrency(currencyService.findById(patternDto.getCurrencyId()));
        Pattern saved = patternRepository.save(pattern);
        return saved.getId();
    }

    @Override
    public PatternDto mapToDto(Pattern pattern) {
        return modelMapper.map(pattern, PatternDto.class);
    }

    @Override
    public Pattern mapFromDto(PatternDto patternDto) {

        return modelMapper.map(patternDto, Pattern.class);
    }
}
