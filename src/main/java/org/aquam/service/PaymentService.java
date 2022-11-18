package org.aquam.service;

import org.aquam.model.Pattern;
import org.aquam.model.dto.PatternDto;
import org.aquam.model.dto.PaymentDto;

import java.util.List;

public interface PaymentService {

    Boolean payForThePattern(PaymentDto paymentDto);
    List<PatternDto> getPatternsByUsername(String username);
}
