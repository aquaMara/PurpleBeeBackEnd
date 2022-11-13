package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Currency;
import org.aquam.model.dto.CurrencyDto;
import org.aquam.repository.CurrencyRepository;
import org.aquam.service.CurrencyService;
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
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final ModelMapper modelMapper;

    @Override
    public CurrencyDto findByName(String name) {
        return currencyRepository.findByName(name).map(this::mapToDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Currency with name " + name + " does not exist"));
    }

    @Override
    public Boolean exists(String name) {
        return currencyRepository.findByName(name).isPresent();
    }

    @Override
    public List<CurrencyDto> readAll() {
        List<Currency> currencies = currencyRepository.findAll();
        if (currencies.isEmpty())
            throw new EntityNotFoundException("No currency");
        return currencies.stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyDto create(Currency currency) {
        if (exists(currency.getName()))
            throw new EntityExistsException("Currency with name " + currency.getName() + " exists");
        Currency saved = currencyRepository.save(currency);
        return mapToDto(saved);
    }

    @Override
    public CurrencyDto mapToDto(Currency currency) {
        return modelMapper.map(currency, CurrencyDto.class);
    }

    @Override
    public Currency mapFromDto(CurrencyDto currencyDto) {
        return modelMapper.map(currencyDto, Currency.class);
    }
}
