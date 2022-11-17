package org.aquam.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aquam.model.DifficultyLevel;

@Data
@AllArgsConstructor
public class PatternDto {

    private Long id;
    private Long creatorId;
    private String name;
    private String littleDescription;
    private Double price;

    private Long craftId;
    private Long categoryId;
    private Long languageId;
    private Long currencyId;
    private DifficultyLevel difficultyLevel;

    private Double avgRate;
    private String imagePath;
    private String pdfPath;

    public PatternDto() {
    }
}
