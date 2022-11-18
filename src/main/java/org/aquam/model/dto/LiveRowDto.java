package org.aquam.model.dto;

import lombok.Data;

@Data
public class LiveRowDto {

    private Long id;
    private int rowNumber;
    private String schema;
    private Boolean isTitleRow;
    private Boolean isInfoRow;
    private Long patternId;
}
