package org.aquam.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aquam.model.DifficultyLevel;

import java.util.Objects;

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

    public PatternDto(Long creatorId, String name, String littleDescription, Double price,
                      Long craftId, Long categoryId, Long languageId, Long currencyId,
                      DifficultyLevel difficultyLevel, Double avgRate) {
        this.creatorId = creatorId;
        this.name = name;
        this.littleDescription = littleDescription;
        this.price = price;
        this.craftId = craftId;
        this.categoryId = categoryId;
        this.languageId = languageId;
        this.currencyId = currencyId;
        this.difficultyLevel = difficultyLevel;
        this.avgRate = avgRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatternDto that = (PatternDto) o;
        return Objects.equals(creatorId, that.creatorId) && Objects.equals(name, that.name) && Objects.equals(littleDescription, that.littleDescription) && Objects.equals(price, that.price) && Objects.equals(craftId, that.craftId) && Objects.equals(categoryId, that.categoryId) && Objects.equals(languageId, that.languageId) && Objects.equals(currencyId, that.currencyId) && difficultyLevel == that.difficultyLevel && Objects.equals(avgRate, that.avgRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creatorId, name, littleDescription, price, craftId, categoryId, languageId, currencyId, difficultyLevel, avgRate);
    }
}
