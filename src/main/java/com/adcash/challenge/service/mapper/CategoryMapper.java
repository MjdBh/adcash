package com.adcash.challenge.service.mapper;

import com.adcash.challenge.domain.Category;
import com.adcash.challenge.service.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper( CategoryMapper.class );

    CategoryDTO mapCategoryToCategoryDTO(Category category);

    Category mapCategoryDTOToCategory(CategoryDTO categoryDTO);
}
