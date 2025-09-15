package org.example.bookshop.service.category;

import org.example.bookshop.dto.category.CategoryDto;
import org.example.bookshop.dto.category.CategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CategoryDto categoryDto);

    CategoryResponseDto update(Long id, CategoryDto categoryDto);

    void deleteById(Long id);
}
