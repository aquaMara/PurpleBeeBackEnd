package org.aquam.service;

import org.aquam.model.Category;
import org.aquam.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto findByName(String name);
    Category findById(Long id);
    Boolean exists(String name);
    List<CategoryDto> read();
    CategoryDto create(Category category);
    CategoryDto mapToDto(Category category);
    Category mapFromDto(CategoryDto categoryDto);
}
