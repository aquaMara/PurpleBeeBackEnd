package org.aquam.service;

import org.aquam.model.Currency;
import org.aquam.model.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {

    CurrencyDto findByName(String name);
    Boolean exists(String name);
    List<CurrencyDto> readAll();
    CurrencyDto create(Currency currency);
    CurrencyDto mapToDto(Currency currency);
    Currency mapFromDto(CurrencyDto currencyDto);
}
