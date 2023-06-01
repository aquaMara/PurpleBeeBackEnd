package org.aquam.configuration;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Category;
import org.aquam.model.Country;
import org.aquam.model.Craft;
import org.aquam.model.Currency;
import org.aquam.model.Language;
import org.aquam.repository.CategoryRepository;
import org.aquam.repository.CountryRepository;
import org.aquam.repository.CraftRepository;
import org.aquam.repository.CurrencyRepository;
import org.aquam.repository.LanguageRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OnStartupCreator {

    private final CraftRepository craftRepository;
    private final CategoryRepository categoryRepository;
    private final CountryRepository countryRepository;
    private final LanguageRepository languageRepository;
    private final CurrencyRepository currencyRepository;

    @PostConstruct
    public void createAdmins() {
        if (craftRepository.findAll().isEmpty()) {
            craftRepository.save(new Craft("Вязание крючком"));
            craftRepository.save(new Craft("Вязание спицами"));
        }
        if (categoryRepository.findAll().isEmpty()) {
            categoryRepository.save(new Category("Домашний декор"));
            categoryRepository.save(new Category("Домашние животные"));
            categoryRepository.save(new Category("Игрушки"));
            categoryRepository.save(new Category("Одежда"));
            categoryRepository.save(new Category("Аксессуары"));
        }
        if (countryRepository.findAll().isEmpty()) {
            countryRepository.save(new Country("Belarus"));
        }
        if (languageRepository.findAll().isEmpty()) {
            languageRepository.save(new Language("Беларуская мова"));
            languageRepository.save(new Language("Русский язык"));
        }
        if (currencyRepository.findAll().isEmpty()) {
            currencyRepository.save(new Currency("BYN"));
        }
    }
}
