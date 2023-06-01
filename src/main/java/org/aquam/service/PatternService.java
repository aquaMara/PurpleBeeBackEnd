package org.aquam.service;

import org.aquam.model.Pattern;
import org.aquam.model.dto.CommentDto;
import org.aquam.model.dto.PatternDto;
import org.aquam.model.dto.PatternModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PatternService {

    Pattern findById(Long id);
    Boolean exists(Long id);
    List<PatternDto> read();
    List<PatternDto> searchByName(String name);
    List<PatternDto> filterByCraft(Long craftId);
    PatternDto readOne(Long id);
    List<PatternDto> readByIds(List<Long> idsOfPatterns);
    PatternModel readPatternModel(Long id);
    Long create(PatternDto patternDto, String username);
    Double setRate(Long patternId, Double value);
    String setComment(Long patternId, CommentDto commentDto);
    List<CommentDto> getComments(Long patternId);
    Boolean uploadImage(Long patternId, MultipartFile multipartFile);
    String generateFilename(String originalFilename);
    String generatePath(String generatedFilename);
    byte[] getImage(Long patternId);
    Boolean lock(Long id);
    PatternDto mapToDto(Pattern pattern);

    Pattern mapFromDto(PatternDto patternDto);
}
