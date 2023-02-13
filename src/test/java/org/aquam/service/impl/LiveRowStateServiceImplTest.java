package org.aquam.service.impl;

import org.aquam.model.AppUser;
import org.aquam.model.AppUserRole;
import org.aquam.model.LiveRowState;
import org.aquam.repository.LiveRowStateRepository;
import org.aquam.service.AppUserService;
import org.aquam.service.LiveRowService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class LiveRowStateServiceImplTest {

    @Mock
    private LiveRowStateRepository liveRowStateRepository;
    @Mock
    private AppUserService appUserService;
    @Mock
    private LiveRowService liveRowService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private LiveRowStateServiceImpl liveRowStateService;

    @Test   // from true to false
    void updateLivePatternRowToNotCrossed() {
        // given
        Long liveRowId = 1L;
        String username = "aquam";
        LiveRowState liveRowState = new LiveRowState();
        given(appUserService.findByUsername(username)).willReturn(createAppUser());
        given(liveRowStateRepository.findLiveRowState(liveRowId, 6L))
                .willReturn(Optional.of(liveRowState))
                .willReturn(Optional.of(liveRowState));
        //doNothing().when(liveRowStateRepository.delete(liveRowState));
        // when
        liveRowStateService.updateLivePatternRow(liveRowId, username);
        // then
        then(liveRowStateRepository).should().delete(liveRowState);
    }

    @Test   // from false to true
    void updateLivePatternRowToCrossed() {
        // given
        Long liveRowId = 1L;
        String username = "aquam";
        LiveRowState toSave = new LiveRowState(liveRowId, 6L);
        given(appUserService.findByUsername(username)).willReturn(createAppUser());
        given(liveRowStateRepository.findLiveRowState(liveRowId, 6L))
                .willReturn(Optional.empty());
        given(liveRowStateRepository.save(toSave)).willReturn(toSave);
        // when
        liveRowStateService.updateLivePatternRow(liveRowId, username);
        // then
        then(liveRowStateRepository).should().save(toSave);
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
}