package org.aquam.service;

import org.aquam.model.Currency;
import org.aquam.model.Pattern;
import org.aquam.model.dto.CommentDto;
import org.aquam.model.dto.PatternDto;
import org.aquam.model.dto.PatternModel;

import java.util.List;

public interface PatternService {

    Pattern findById(Long id);
    Boolean exists(Long id);
    List<PatternDto> read();
    List<PatternDto> searchByName(String name);
    PatternDto readOne(Long id);
    List<PatternDto> readByIds(List<Long> idsOfPatterns);
    PatternModel readPatternModel(Long id);
    Long create(PatternDto patternDto, String username);
    Double setRate(Long patternId, Double value);
    String setComment(Long patternId, CommentDto commentDto);
    List<CommentDto> getComments(Long patternId);
    PatternDto mapToDto(Pattern pattern);
    Pattern mapFromDto(PatternDto patternDto);
}
