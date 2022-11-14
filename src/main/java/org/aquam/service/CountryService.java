package org.aquam.service;

import org.aquam.model.Country;
import org.aquam.model.dto.CountryDto;

import java.util.List;

public interface CountryService {

    CountryDto findByName(String name);
    Boolean exists(String name);
    List<CountryDto> read();
    CountryDto create(Country country);
    CountryDto mapToDto(Country country);
    Country mapFromDto(CountryDto countryDto);
}
