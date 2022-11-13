package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.service.CurrencyService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
}
