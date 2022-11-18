package org.aquam.service;

import org.aquam.model.Craft;
import org.aquam.model.LiveRow;
import org.aquam.model.Pattern;
import org.aquam.model.dto.CraftDto;
import org.aquam.model.dto.LiveRowDto;
import org.aquam.model.dto.PatternDto;

import java.util.List;

public interface LiveRowService {

    List<LiveRowDto> create(List<LiveRowDto> liveRowsDto, Long patternId);
    List<LiveRow> readPatternLiveRows(Long patternId);
    LiveRowDto mapToDto(LiveRow liveRow);
    LiveRow mapFromDto(LiveRowDto liveRowDto);
}
