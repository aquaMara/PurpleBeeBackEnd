package org.aquam.service.impl;

import org.aquam.model.AppUser;
import org.aquam.model.DifficultyLevel;
import org.aquam.model.Pattern;
import org.aquam.model.Payment;
import org.aquam.model.dto.PaymentDto;
import org.aquam.repository.PaymentRepository;
import org.aquam.service.AppUserService;
import org.aquam.service.PatternService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AppUserService appUserService;
    @Mock
    private PatternService patternService;
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void payForThePattern() {
        // given
        Payment payment = createPayment();
        given(patternService.findById(23L)).willReturn(createPattern());
        given(appUserService.findByUsername("aquam")).willReturn(createAppUser());
        given(paymentRepository.save(payment)).willReturn(payment);
        // when
        paymentService.payForThePattern(createPaymentDto());
        // then
        then(paymentRepository).should().save(payment);
    }

    public PaymentDto createPaymentDto() {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPayment(10.0);
        paymentDto.setUsername("aquam");
        paymentDto.setPatternId(23L);
        return paymentDto;
    }

    public Payment createPayment() {
        Payment payment = new Payment();
        payment.setPayment(10.0);
        payment.setAppUser(createAppUser());
        payment.setPattern(createPattern());
        return payment;
    }

    public Pattern createPattern() {
        Pattern pattern = new Pattern("Small bear", "This pattern is very cool",
                DifficultyLevel.BEGINNER, 10.0);
        pattern.setId(23L);
        return pattern;
    }

    public AppUser createAppUser() {
        AppUser appUser = new AppUser("aquam", "Cheese2017*", "beemail@gmail.com");
        appUser.setId(6L);
        return appUser;
    }
}