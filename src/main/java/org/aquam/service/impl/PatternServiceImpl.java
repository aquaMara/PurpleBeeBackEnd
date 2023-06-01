package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.exception.FileUploadingException;
import org.aquam.model.*;
import org.aquam.model.dto.CommentDto;
import org.aquam.model.dto.PatternDto;
import org.aquam.model.dto.PatternModel;
import org.aquam.repository.CommentRepository;
import org.aquam.repository.PatternRepository;
import org.aquam.service.AppUserService;
import org.aquam.service.CategoryService;
import org.aquam.service.CraftService;
import org.aquam.service.CurrencyService;
import org.aquam.service.LanguageService;
import org.aquam.service.PatternService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PatternServiceImpl implements PatternService {

    private final PatternRepository patternRepository;
    private final ModelMapper modelMapper;

    private final AppUserService appUserService;
    private final CraftService craftService;
    private final CategoryService categoryService;
    private final LanguageService languageService;
    private final CurrencyService currencyService;

    private final CommentRepository commentRepository;

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
        List<Pattern> patterns = patternRepository.findAll()
                .stream().filter(p -> !p.getLocked())
                .toList();
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
        boolean empty = patterns.stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase())
                        && s.getLocked().equals(false))
                .toList().isEmpty();
        if (!empty) {
            return patterns.stream()
                    .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase())
                            && s.getLocked().equals(false))
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("No patterns");
        }
    }

    @Override
    public List<PatternDto> filterByCraft(Long craftId) {
        List<Pattern> patterns = patternRepository.findAll();
        Craft craft = craftService.findById(craftId);
        if (patterns.isEmpty())
            throw new EntityNotFoundException("No patterns");
        List<Pattern> collect = patterns.stream()
                .filter(p -> p.getCraft().equals(craft)
                        && p.getLocked().equals(false))
                .toList();
        if (collect.isEmpty())
            throw new EntityNotFoundException("No patterns for craft with id: " + craft + " found");
        else
            return collect.stream()
                    .map(this::mapToDto)
                    .toList();
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
                pattern.getImagePath(),
                pattern.getPdfPath()
        );
        patternModel.setCreatorUsername(pattern.getCreator().getUsername());
        patternModel.setCraftName(pattern.getCraft().getName());
        patternModel.setCategoryName(pattern.getCategory().getName());
        patternModel.setLanguageName(pattern.getCategory().getName());
        patternModel.setCurrencyName(pattern.getCurrency().getName());
        patternModel.setAvgRate(pattern.getNumRate() == 0 ? 0 : pattern.getSumRate() / pattern.getNumRate());
        return patternModel;
    }
/*
return patterns.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
 */
    @Override
    public Long create(PatternDto patternDto, String username) {
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
    public Double setRate(Long patternId, Double value) {  // sum pattern rate
        Pattern pattern = findById(patternId);
        pattern.setSumRate(pattern.getSumRate() + value);
        pattern.setNumRate(pattern.getNumRate() + 1);
        double rate = pattern.getSumRate() / pattern.getNumRate();
        patternRepository.save(pattern);
        return rate;
    }

    @Override
    public String setComment(Long patternId, CommentDto commentDto) {
        Pattern pattern = findById(patternId);
        Comment comment = new Comment(commentDto.getBody(), pattern);
        Comment savedComment = commentRepository.save(comment);
        pattern.getComments().add(savedComment);
        Pattern save = patternRepository.save(pattern);
        return "success";
    }

    @Override
    public List<CommentDto> getComments(Long patternId) {
        Pattern pattern = findById(patternId);
        List<Comment> comments = pattern.getComments();
        if (!comments.isEmpty()) {
            return comments.stream()
                    .map(comment -> modelMapper.map(comment, CommentDto.class))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Boolean uploadImage(Long patternId, MultipartFile multipartFile) {
        Pattern pattern = findById(patternId);
        String filename = generateFilename(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String path = generatePath(filename);
        pattern.setImagePath(path);
        try {
            multipartFile.transferTo(new File(path));
        } catch (IOException e) {
            throw new FileUploadingException("File was not saved in storage");
        }
        patternRepository.save(pattern);
        return true;
    }

    @Override
    public String generateFilename(String originalFilename) {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        String noWhitespaces = originalFilename.replaceAll("\\s","");
        return "" + dateTimeNow.getYear()
                + dateTimeNow.getDayOfYear() + dateTimeNow.getHour()
                + dateTimeNow.getMinute() + dateTimeNow.getSecond()
                + noWhitespaces;
    }

    @Override
    public String generatePath(String generatedFilename) {
        String path = new File("").getAbsolutePath();
        path += "\\" + generatedFilename;
        return path;
    }

    @Override
    public byte[] getImage(Long patternId) {
        Pattern pattern = findById(patternId);
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(new File(pattern.getImagePath()).toPath());
        } catch (IOException e) {
            throw new FileUploadingException("File can't be read");
        }
        return bytes;
    }

    @Override
    public Boolean lock(Long id) {
        Pattern pattern = findById(id);
        pattern.setLocked(true);
        patternRepository.save(pattern);
        return true;
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
