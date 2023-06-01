package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.AppUser;
import org.aquam.model.Pattern;
import org.aquam.model.Payment;
import org.aquam.model.dto.PatternDto;
import org.aquam.model.dto.PaymentDto;
import org.aquam.repository.PaymentRepository;
import org.aquam.service.AppUserService;
import org.aquam.service.PatternService;
import org.aquam.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    private final AppUserService appUserService;
    private final PatternService patternService;

    @Override
    public Boolean payForThePattern(PaymentDto paymentDto) {
        Pattern pattern = patternService.findById(paymentDto.getPatternId());
        AppUser user = appUserService.findByUsername(paymentDto.getUsername());
        Payment payment = new Payment(paymentDto.getPayment(),user, pattern);
        Payment saved = paymentRepository.save(payment);
        return true;
    }

    @Override
    public List<PatternDto> getPatternsByUsername(String username) {
        AppUser user = appUserService.findByUsername(username);
        List<Payment> payments = user.getPayments();
        List<Long> patternIdsThatUserPurchased = payments.stream()
                .map(m -> m.getPattern().getId())
                .collect(Collectors.toList());
        return patternService
                .readByIds(patternIdsThatUserPurchased);
    }

    @Override
    public Boolean isPatternAcquired(Long patternId) {
        String username = getAuth().getName();
        List<PatternDto> patternsByUsername = getPatternsByUsername(username);
        List<PatternDto> list = patternsByUsername
                .stream()
                .filter(patternDto -> Objects.equals(patternDto.getId(), patternId))
                .toList();
        return !list.isEmpty();
    }

    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
