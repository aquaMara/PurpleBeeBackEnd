package org.aquam.service;

import org.aquam.model.dto.LiveRowModel;
import org.aquam.model.dto.PatternUser;

import java.util.List;

public interface LiveRowStateService {

    List<LiveRowModel> readLivePattern(Long patternId, String username);
    Boolean updateLivePatternRow(Long liveRowId, String username);
    Boolean isCrossed(Long liveRowId, Long userId);
}
