package com.adcash.challenge.web.rest;

import com.adcash.challenge.config.Constants;
import com.adcash.challenge.domain.Category;
import com.adcash.challenge.domain.Product;
import com.adcash.challenge.repository.CategoryRepository;
import com.adcash.challenge.repository.ProductRepository;
import com.adcash.challenge.service.ProductService;
import com.adcash.challenge.service.dto.CategoryDTO;
import com.adcash.challenge.service.dto.ProductDTO;
import com.adcash.challenge.service.mapper.ProductMapper;
import com.adcash.challenge.web.rest.errors.BadRequestException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;

/**
 * REST controller for managing {@link com.adcash.challenge.domain.Product}.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {
    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResource(ProductService productService, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    /**
     * {@code POST  /products} : Create a new product.
     *
     * @param product the product to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new product, or with status {@code 400 (Bad Request)} if the product is duplicate.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/products")
    @PreAuthorize("hasRole(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<Void> createProduct(@RequestBody ProductDTO product) throws URISyntaxException {
        log.debug("REST request to save Product : {}", product);
        if (productService.existByIdentifier(product.getIdentifier())) {
            throw new BadRequestException("A new product already exist");
        }


        Product productEntity = ProductMapper.INSTANCE.mapProductDTOToProduct(product);
        productEntity.setCategories(getCategories(product));
        productService.save(productEntity);
        return ResponseEntity.created(new URI("/api/products/" + product.getIdentifier())).build();
    }

    /**
     * {@code PUT  /products} : Updates an existing product.
     *
     * @param product the product to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated product,
     * or with status {@code 400 (Bad Request)} if the product is not valid,
     * or with status {@code 500 (Internal Server Error)} if the product couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/products")
    @PreAuthorize("hasRole(\"" + Constants.ADMIN + "\")")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDTO product) {
        log.debug("REST request to update Product : {}", product);
        if (!productService.existByIdentifier(product.getIdentifier())) {
            throw new BadRequestException("Product not exist");
        }

        Product productEntity = ProductMapper.INSTANCE.mapProductDTOToProduct(product);
        productEntity.setId(productService.getProductId(product.getIdentifier()));
        productEntity.setCategories(getCategories(product));
        productService.save(productEntity);
        return ResponseEntity.ok().build();

    }

    private Set<Category> getCategories(@RequestBody ProductDTO product) {
        Set<Category> categories = new LinkedHashSet<>();
        if(product.getCategories()!=null) {
            for (CategoryDTO categoryDTO : product.getCategories()) {
                Category category = categoryRepository.findOneByIdentifier(categoryDTO.getIdentifier());
                if (category != null)
                    categories.add(category);
                else
                    throw new BadRequestException("Invalid category " + categoryDTO.getIdentifier());
            }
        }
        return categories;
    }

    /**
     * {@code GET  /public/products} : get all the products.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("/public/products")
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        log.debug("REST request to get all Products");
        return productService.get(pageable).map(ProductMapper.INSTANCE::mapProductToProductDTO);
    }

    /**
     * {@code GET  /public/products} : get all the products.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
    @GetMapping("/public/products/category/{identifier}")
    public List<ProductDTO> getAllProductsByCategory(@PathVariable("identifier") String identifier) {
        log.debug("REST request to get all Products of category {}", identifier);
        return ProductMapper.INSTANCE.mapProductToProductDTO(productService.getProductsByCategory(identifier));
    }


    /**
     * {@code GET  /products/:id} : get the "id" product.
     *
     * @param identifier the id of the product to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the product, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public/products/{identifier}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String identifier) {

        Optional<Product> product = productService.findOneWithEagerRelationships(identifier);
        if (!product.isPresent())
            throw new BadRequestException("Product not exist");

        return ResponseEntity.ok(product.map(ProductMapper.INSTANCE::mapProductToProductDTO).get());
    }

    /**
     * {@code DELETE  /products/:identifier} : delete the "id" product.
     *
     * @param identifier the id of the product to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products/{identifier}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String identifier) {
        log.debug("REST request to delete Product : {}", identifier);
        productService.delete(identifier);
        return ResponseEntity.noContent().build();
    }
}

