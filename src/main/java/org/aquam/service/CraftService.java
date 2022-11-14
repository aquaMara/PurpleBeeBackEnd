package org.aquam.service;

import org.aquam.model.Craft;
import org.aquam.model.dto.CraftDto;

import java.util.List;

public interface CraftService {

    CraftDto findByName(String name);
    Boolean exists(String name);
    List<CraftDto> read();
    CraftDto create(Craft craft);
    CraftDto mapToDto(Craft craft);
    Craft mapFromDto(CraftDto craftDto);
}
