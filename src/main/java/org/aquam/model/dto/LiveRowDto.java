package org.aquam.model.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class LiveRowDto {

    private Long id;
    private int rowNumber;
    private String schema;
    private Boolean isTitleRow;
    private Boolean isInfoRow;
    private Long patternId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveRowDto that = (LiveRowDto) o;
        return rowNumber == that.rowNumber && Objects.equals(schema, that.schema) && Objects.equals(isTitleRow, that.isTitleRow) && Objects.equals(isInfoRow, that.isInfoRow) && Objects.equals(patternId, that.patternId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNumber, schema, isTitleRow, isInfoRow, patternId);
    }
}
