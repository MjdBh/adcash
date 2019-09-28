package com.adcash.challenge.web.rest;

import com.adcash.challenge.domain.Category;
import com.adcash.challenge.service.CategoryService;
import com.adcash.challenge.service.dto.CategoryDTO;
import com.adcash.challenge.service.mapper.CategoryMapper;
import com.adcash.challenge.web.rest.errors.BadRequestException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * REST controller for managing {@link com.adcash.challenge.domain.Category}.
 */
@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * {@code POST  /categories} : Create a new category.
     *
     * @param category the category to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new category, or with status {@code 400 (Bad Request)} if the category has already an ID.
     * @throws BadRequestException if exist duplicate identifier .
     */
    @PostMapping("/categories")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryDTO category) throws URISyntaxException {
        log.debug("REST request to save Category : {}", category);

        if (categoryService.existByIdentifier(category.getIdentifier()))
            throw new BadRequestException("Exist category identifier.");

        Category categoryEntity = CategoryMapper.INSTANCE.mapCategoryDTOToCategory(category);
        categoryService.save(categoryEntity);
        return ResponseEntity.created(new URI("/api/public/categories/" + category.getIdentifier())).build();
    }

    /**
     * {@code PUT  /categories} : Updates an existing category.
     *
     * @param category the category to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated category,
     * or with status {@code 400 (Bad Request)} if the category is not valid,
     * or with status {@code 500 (Internal Server Error)} if the category couldn't be updated.
     */
    @PutMapping("/categories")
    public ResponseEntity<Void> updateCategory(@Valid @RequestBody CategoryDTO category) {
        log.debug("REST request to update Category : {}", category);
        if (!categoryService.existByIdentifier(category.getIdentifier())) {
            throw new BadRequestException("Invalid identifier");
        }

        categoryService.updateCategory(category.getIdentifier(),category.getName());
        return ResponseEntity.ok().build();
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/public/categories")
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        log.debug("REST request to get all Categories");
        return categoryService.get(pageable).map(CategoryMapper.INSTANCE::mapCategoryToCategoryDTO);
    }


    /**
     * {@code DELETE  /categories/:identifier} : delete the "id" category.
     *
     * @param identifier of the category to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories/{identifier}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String identifier) {
        log.debug("REST request to delete Category : {}", identifier);
        categoryService.delete(identifier);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public/categories/{identifier}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable String identifier) {
        log.debug("REST request to get Category : {}", identifier);
        Category category = categoryService.getByIdentifier(identifier);
        if (category == null)
            throw new BadRequestException("Invalid category");

        return ResponseEntity.ok(CategoryMapper.INSTANCE.mapCategoryToCategoryDTO(category));
    }
}
