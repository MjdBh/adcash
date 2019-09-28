package com.adcash.challenge.service;


import com.adcash.challenge.domain.Product;
import com.adcash.challenge.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void delete(String identifier){
        productRepository.deleteByIdentifier(identifier);
    }

    public Page<Product> get(Pageable pageable){ return productRepository.findAllWithEagerRelationships(pageable);}

    public Product save(Product category){
        return productRepository.save(category);
    }

    public boolean existByIdentifier(String identifier){  return  productRepository.existsByIdentifier(identifier); }

    public Optional<Product> findOneWithEagerRelationships(String identifier){return productRepository.findOneWithEagerRelationships(identifier);}

    public List<Product> getProductsByCategory(String identifier){return  productRepository.findAllWithCategory(identifier);}

    public Long getProductId(String identifier) {return productRepository.getProductId(identifier);}
}
