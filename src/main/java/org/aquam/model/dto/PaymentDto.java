package org.aquam.model.dto;

import lombok.Data;

@Data
public class PaymentDto {

    private Long id;
    private Double payment;
    private String username;
    private Long patternId;
}
