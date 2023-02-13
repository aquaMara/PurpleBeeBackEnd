package org.aquam.service.impl;

import org.aquam.model.AppUser;
import org.aquam.model.AppUserRole;
import org.aquam.model.Category;
import org.aquam.model.Comment;
import org.aquam.model.Craft;
import org.aquam.model.Currency;
import org.aquam.model.DifficultyLevel;
import org.aquam.model.Language;
import org.aquam.model.Pattern;
import org.aquam.model.dto.CommentDto;
import org.aquam.model.dto.PatternDto;
import org.aquam.repository.CommentRepository;
import org.aquam.repository.PatternRepository;
import org.aquam.service.AppUserService;
import org.aquam.service.CategoryService;
import org.aquam.service.CraftService;
import org.aquam.service.CurrencyService;
import org.aquam.service.LanguageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatternServiceImplTest {

    @Mock
    private PatternRepository patternRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AppUserService appUserService;
    @Mock
    private CraftService craftService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private LanguageService languageService;
    @Mock
    private CurrencyService currencyService;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private PatternServiceImpl patternService;

    @Test
    void searchByName() {
        // given
        String name = "bear";
        given(patternRepository.findAll()).willReturn(createPatternList());
        // when
        List<PatternDto> patternDtos = patternService.searchByName(name);
        // then
        assertEquals(2, patternDtos.size());
    }

    @Test
    void searchByNameNoPatterns() {
        // given
        String name = "bear";
        given(patternRepository.findAll()).willReturn(new ArrayList<>());
        // when
        // then
        assertThrows(EntityNotFoundException.class,
                () -> patternService.searchByName(name));
    }

    @Test
    void searchByNameNoCorrespondingPatterns() {
        // given
        String name = "999";
        given(patternRepository.findAll()).willReturn(createPatternList());
        // when
        // then
        assertThrows(EntityNotFoundException.class,
                () -> patternService.searchByName(name));
    }

    @Test
    void create() {
        // given
        given(appUserService.findByUsername("aquam")).willReturn(createAppUser());
        given(craftService.findById(1L)).willReturn(createCraft());
        given(categoryService.findById(2L)).willReturn(createCategory());
        given(languageService.findById(3L)).willReturn(createLanguage());
        given(currencyService.findById(4L)).willReturn(createCurrency());
        Pattern saved = createPattern();
        saved.setId(6L);
        given(patternRepository.save(createPattern())).willReturn(saved);
        // when
        patternService.create(createPatternDto(), "aquam");
        // then
        then(patternRepository).should().save(createPattern());
    }

    @Test
    void setRate() {
        // given
        Pattern pattern = createPattern();
        pattern.setId(23L);
        given(patternRepository.findById(anyLong())).willReturn(Optional.of(pattern));
        given(patternRepository.save(createPattern())).willReturn(pattern);
        // when
        patternService.setRate(23L, 5.0);
        // then
        then(patternRepository).should().save(createPattern());
    }

    @Test
    void setRatePatternNotFound() {
        // given
        given(patternRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        // then
        assertThrows(EntityNotFoundException.class,
                () -> patternService.setRate(anyLong(), 5.0));
    }

    @Test
    void setComment() {
        // given
        Pattern pattern = createPattern();
        pattern.setId(23L);
        given(patternRepository.findById(anyLong())).willReturn(Optional.of(pattern));
        CommentDto commentDto = createCommentDto();
        Comment comment = new Comment(commentDto.getBody(), pattern);
        given(commentRepository.save(comment)).willReturn(comment);
        pattern.getComments().add(comment);
        given(patternRepository.save(pattern)).willReturn(pattern);
        // when
        patternService.setComment(23L, commentDto);
        // then
        then(patternRepository).should().save(pattern);
    }

    @Test
    void setCommentThrowsNotFound() {
        // given
        given(patternRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        // then
        assertThrows(EntityNotFoundException.class,
                () -> patternService.setRate(anyLong(), 5.0));
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

    public PatternDto createPatternDto() {
        return new PatternDto(6L, "Small bear", "This pattern is very cool", 10.0,
                1L, 2L, 3L, 4L, DifficultyLevel.BEGINNER, 0.0);
    }

    public Pattern createPattern() {
        Pattern pattern = new Pattern("Small bear", "This pattern is very cool",
                DifficultyLevel.BEGINNER, 10.0);
        pattern.setCreator(createAppUser());
        pattern.setCraft(createCraft());
        pattern.setCategory(createCategory());
        pattern.setLanguage(createLanguage());
        pattern.setCurrency(createCurrency());
        pattern.setComments(new ArrayList<>());
        return pattern;
    }

    public Craft createCraft() {
        Craft craft = new Craft();
        craft.setId(1L);
        craft.setName("Crochet");
        return craft;
    }

    public Category createCategory() {
        Category category = new Category();
        category.setId(2L);
        category.setName("Toys");
        return category;
    }

    public Language createLanguage() {
        Language language = new Language();
        language.setId(3L);
        language.setName("Belarusian");
        return language;
    }

    public Currency createCurrency() {
        Currency currency = new Currency();
        currency.setId(4L);
        currency.setName("BYN");
        return currency;
    }

    public Comment createComment() {
        Comment comment = new Comment();
        Pattern pattern = createPattern();
        pattern.setId(23L);
        comment.setPattern(pattern);
        comment.setAppUser(createAppUser());
        comment.setBody("I want to ask about your service");
        return comment;
    }

    public CommentDto createCommentDto() {
        CommentDto commentDto = new CommentDto();
        commentDto.setPatternId(23L);
        commentDto.setBody("I want to ask about your service");
        return commentDto;
    }

    public List<Pattern> createPatternList() {
        Pattern pattern1 = new Pattern("Small bear", "This pattern is very cool",
                DifficultyLevel.BEGINNER, 10.0);
        Pattern pattern2 = new Pattern("Very small bear", "This pattern is very cool",
                DifficultyLevel.BEGINNER, 10.0);
        Pattern pattern3 = new Pattern("Bee", "This pattern is very cool",
                DifficultyLevel.BEGINNER, 10.0);
        return Arrays.asList(pattern1, pattern2, pattern3);
    }
}