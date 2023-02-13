package org.aquam.model.dto;

import lombok.Data;

@Data
public class RateDto {

    private Long id;
    private int value;
    private Long userId;
    private Long patternId;
}
