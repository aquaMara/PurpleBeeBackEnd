package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.LiveRow;
import org.aquam.model.Pattern;
import org.aquam.model.dto.LiveRowDto;
import org.aquam.model.dto.PatternDto;
import org.aquam.repository.LiveRowRepository;
import org.aquam.service.LiveRowService;
import org.aquam.service.PatternService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LiveRowServiceImpl implements LiveRowService {

    private final LiveRowRepository liveRowRepository;
    private final PatternService patternService;
    private final ModelMapper modelMapper;

    @Override
    public List<LiveRowDto> read() {
        // todo: find all live rows by pattern id
        return null;
    }

    @Override
    public List<LiveRowDto> create(List<LiveRowDto> liveRowsDto, Long patternId) {
        Pattern pattern = patternService.findById(patternId);
        // todo: check if pattern already has live rows and delete or replace
        List<LiveRow> liveRows = liveRowsDto.stream()
                .map(this::mapFromDto).collect(Collectors.toList());
        List <LiveRow> savedLiveRows = new ArrayList<>();
        for (int i = 0; i < liveRows.size(); i++) {
            LiveRow current = liveRows.get(i);
            current.setPattern(pattern);
            current.setRowNumber(i);
            LiveRow saved = liveRowRepository.save(current);
            savedLiveRows.add(saved);
        }
        List<LiveRowDto> savedLiveRowsDto = savedLiveRows.stream()
                .map(this::mapToDto).collect(Collectors.toList());
        return savedLiveRowsDto;
    }

    @Override
    public LiveRowDto mapToDto(LiveRow liveRow) {
        return modelMapper.map(liveRow, LiveRowDto.class);
    }

    @Override
    public LiveRow mapFromDto(LiveRowDto liveRowDto) {
        return modelMapper.map(liveRowDto, LiveRow.class);
    }
}
