package org.aquam.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquam.model.Category;
import org.aquam.model.dto.CategoryDto;
import org.aquam.repository.CategoryRepository;
import org.aquam.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto findByName(String name) {
        return categoryRepository.findByName(name).map(this::mapToDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Category with name " + name + " does not exist"));
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category with id " + id + " does not exist"));
    }

    @Override
    public Boolean exists(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

    @Override
    public List<CategoryDto> read() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty())
            throw new EntityNotFoundException("No categories");
        return categories.stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto create(Category category) {
        if (exists(category.getName()))
            throw new EntityExistsException("Category with name " + category.getName() + " exists");
        Category saved = categoryRepository.save(category);
        return mapToDto(saved);
    }

    @Override
    public CategoryDto mapToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public Category mapFromDto(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}
