package org.aquam.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CraftDto {

    private Long id;
    @NotBlank(message = "Name can not be empty")
    private String name;
}
