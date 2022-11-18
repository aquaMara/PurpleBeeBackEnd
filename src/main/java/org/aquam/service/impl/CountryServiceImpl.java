package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Country;
import org.aquam.model.dto.CountryDto;
import org.aquam.repository.CountryRepository;
import org.aquam.service.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CountryDto findByName(String name) {
        return countryRepository.findByName(name).map(this::mapToDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Country with name " + name + " does not exist"));
    }

    @Override
    public Country findById(Long id) {
        return countryRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException("Country with id " + id + " does not exist"));
    }

    @Override
    public Boolean exists(String name) {
        return countryRepository.findByName(name).isPresent();
    }

    @Override
    public List<CountryDto> read() {
        List<Country> countries = countryRepository.findAll();
        if (countries.isEmpty())
            throw new EntityNotFoundException("No countries");
        return countries.stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CountryDto create(Country country) {
        if (exists(country.getName()))
            throw new EntityExistsException("Country with name " + country.getName() + " exists");
        Country saved = countryRepository.save(country);
        return mapToDto(saved);
    }

    @Override
    public CountryDto mapToDto(Country country) {
        return modelMapper.map(country, CountryDto.class);
    }

    @Override
    public Country mapFromDto(CountryDto countryDto) {
        return modelMapper.map(countryDto, Country.class);
    }
}
