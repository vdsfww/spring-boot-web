package org.example.bookshop.mapper;

import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.category.CategoryDto;
import org.example.bookshop.dto.category.CategoryResponseDto;
import org.example.bookshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toCategoryResponseDto(Category category);

    Category toModel(CategoryDto categoryDto);

    void updateCategoryFromDto(CategoryDto requestDto, @MappingTarget Category category);
}
