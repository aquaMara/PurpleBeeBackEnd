package org.aquam.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
@EnableAsync
public class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = validatorFactory.usingContext().getValidator();
        return validator;
    }
}
