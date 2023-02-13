package org.aquam.service.impl;

import org.aquam.model.AppUser;
import org.aquam.model.AppUserRole;
import org.aquam.model.Category;
import org.aquam.model.Craft;
import org.aquam.model.Currency;
import org.aquam.model.DifficultyLevel;
import org.aquam.model.Language;
import org.aquam.model.LiveRow;
import org.aquam.model.Pattern;
import org.aquam.model.dto.LiveRowDto;
import org.aquam.repository.LiveRowRepository;
import org.aquam.service.PatternService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LiveRowServiceImplTest {

    @Mock
    private LiveRowRepository liveRowRepository;
    @Mock
    private PatternService patternService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private LiveRowServiceImpl liveRowService;

    @Test
    void create() {
        // given
        Pattern pattern = createPattern();
        LiveRow liveRow = createLiveRow();
        LiveRowDto liveRowDto = createLiveRowDto();
        List<LiveRowDto> liveRowDtos = List.of(liveRowDto);
        given(patternService.findById(23L)).willReturn(pattern);
        given(modelMapper.map(liveRowDto, LiveRow.class)).willReturn(liveRow);
        given(liveRowRepository.save(liveRow)).willReturn(liveRow);
        // add row number - no
        given(modelMapper.map(liveRow, LiveRowDto.class)).willReturn(liveRowDto);
        // when
        liveRowService.create(liveRowDtos, 23L);
        // then
        assertEquals(1, liveRowDtos.size());
        assertEquals("3 sc", liveRowDtos.get(0).getSchema());

    }

    public LiveRow createLiveRow() {
        LiveRow liveRow = new LiveRow();
        liveRow.setRowNumber(0);
        liveRow.setSchema("3 sc");
        liveRow.setIsInfoRow(false);
        liveRow.setIsTitleRow(false);
        return liveRow;
    }

    public LiveRowDto createLiveRowDto() {
        LiveRowDto liveRowDto = new LiveRowDto();
        liveRowDto.setRowNumber(0);
        liveRowDto.setSchema("3 sc");
        liveRowDto.setIsInfoRow(false);
        liveRowDto.setIsTitleRow(false);
        return liveRowDto;
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

    public AppUser createAppUser() {
        AppUser user = new AppUser("aquam", "cheeseCake16!", "aquam@gmail.com");
        user.setLocked(false);
        user.setEnabled(true);
        user.setAppUserRole(AppUserRole.USER);
        user.setRegistrationDate(LocalDateTime.now());
        user.setId(6L);
        return user;
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
}