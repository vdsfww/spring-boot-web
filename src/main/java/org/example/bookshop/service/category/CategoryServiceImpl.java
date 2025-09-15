package org.example.bookshop.service.category;

import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.category.CategoryDto;
import org.example.bookshop.dto.category.CategoryResponseDto;
import org.example.bookshop.exception.EntityNotFoundException;
import org.example.bookshop.mapper.CategoryMapper;
import org.example.bookshop.model.Category;
import org.example.bookshop.repository.category.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toCategoryResponseDto);
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find Category with id: " + id)
        );
        return categoryMapper.toCategoryResponseDto(category);
    }

    @Override
    public CategoryResponseDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.toModel(categoryDto);
        return categoryMapper.toCategoryResponseDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find Category with id: " + id)
        );
        categoryMapper.updateCategoryFromDto(categoryDto, category);
        return categoryMapper.toCategoryResponseDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
