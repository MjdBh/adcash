package com.adcash.challenge.service;

import com.adcash.challenge.domain.Category;
import com.adcash.challenge.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void updateCategory(String identifier , String name){categoryRepository.update(identifier,name);}

    public void delete(String identifier){
        categoryRepository.deleteByIdentifier(identifier);
    }

    public Page<Category> get(Pageable pageable){
        return categoryRepository.findAll(pageable);
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public boolean existByIdentifier(String identifier){return  categoryRepository.existsByIdentifierEquals(identifier); }

    public Category getByIdentifier(String identifier){return  categoryRepository.findOneByIdentifier(identifier);}

}
