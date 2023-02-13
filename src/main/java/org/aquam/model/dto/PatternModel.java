package org.aquam.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aquam.model.DifficultyLevel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatternModel {

    private Long id;
    private String creatorUsername;
    private String name;
    private String littleDescription;
    private Double price;

    private String craftName;
    private String categoryName;
    private String languageName;
    private String currencyName;
    private DifficultyLevel difficultyLevel;

    private Double avgRate;
    private String imagePath;
    private String pdfPath;

    public PatternModel(Long id, String name, String littleDescription,
                        Double price, DifficultyLevel difficultyLevel,
                        String imagePath, String pdfPath) {
        this.id = id;
        this.name = name;
        this.littleDescription = littleDescription;
        this.price = price;
        this.difficultyLevel = difficultyLevel;
        this.imagePath = imagePath;
        this.pdfPath = pdfPath;
    }
}
