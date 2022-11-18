package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.LiveRow;
import org.aquam.model.LiveRowState;
import org.aquam.model.dto.LiveRowModel;
import org.aquam.model.dto.PatternUser;
import org.aquam.repository.LiveRowStateRepository;
import org.aquam.service.AppUserService;
import org.aquam.service.LiveRowService;
import org.aquam.service.LiveRowStateService;
import org.aquam.service.PatternService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class LiveRowStateServiceImpl implements LiveRowStateService {

    private final LiveRowStateRepository liveRowStateRepository;
    private final AppUserService appUserService;
    private final LiveRowService liveRowService;
    private final ModelMapper modelMapper;
/*
for (LiveRow liveRow : liveRows) {
            LiveRowModel livePatternRow = new LiveRowModel(
                    liveRow.getId(),
                    liveRow.getRowNumber(),
                    liveRow.getSchema(),
                    liveRow.getIsTitleRow(),
                    liveRow.getIsInfoRow()
            );
        }
 */
    /*

        for (int i = 0; i < livePattern.size(); i++) {
            Long liveRowId = livePattern.get(i).getLiveRowId();
            livePattern.get(i).setIsCrossed(isCrossed(liveRowId, userId));
        }
     */

    @Override
    public List<LiveRowModel> readLivePattern(Long patternId, String username) {
        Long userId = appUserService.findByUsername(username).getId();
        // get live rows of the pattern
        List<LiveRow> liveRows = liveRowService
                .readPatternLiveRows(patternId);
        // create a part of the model to frontend (without crossed rows)
        List<LiveRowModel> livePattern = liveRows.stream()
                .map(this::mapToLivePattern)
                .collect(Collectors.toList());
        // set isCrossed for every element in livePattern
        for (LiveRowModel lm : livePattern) {
            Long liveRowId = lm.getLiveRowId();
            lm.setIsCrossed(isCrossed(liveRowId, userId));
        }
        return livePattern;
    }

    @Override
    public Boolean updateLivePatternRow(Long liveRowId, String username) {
        Long userId = appUserService.findByUsername(username).getId();
        LiveRowState liveRowState = new LiveRowState();
        if (isCrossed(liveRowId, userId)) {    // exists in repo
            liveRowState = liveRowStateRepository
                    .findLiveRowState(liveRowId, userId).get();
            liveRowStateRepository.delete(liveRowState);
        } else {    // doesn't exist in repo
            liveRowState = new LiveRowState(liveRowId, userId);
            liveRowStateRepository.save(liveRowState);
        }
        return true;
    }

    @Override
    public Boolean isCrossed(Long liveRowId, Long userId) {
        return liveRowStateRepository
                .findLiveRowState(liveRowId, userId)
                .isPresent();
    }

    public LiveRowModel mapToLivePattern(LiveRow liveRow) {
        return modelMapper.map(liveRow, LiveRowModel.class);
    }
}
