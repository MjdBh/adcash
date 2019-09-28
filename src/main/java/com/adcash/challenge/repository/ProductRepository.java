package com.adcash.challenge.repository;
import com.adcash.challenge.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**findOneWithEagerRelationships
 * Spring Data  repository for the Product entity.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select distinct product from Product product left join fetch product.categories",
        countQuery = "select count(distinct product) from Product product")
    Page<Product> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct product from Product product left join fetch product.categories")
    List<Product> findAllWithEagerRelationships();

    @Query("select product from Product product left join fetch product.categories where product.id =:id")
    Optional<Product> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select product from Product product left join fetch product.categories where product.identifier =:identifier")
    Optional<Product> findOneWithEagerRelationships(@Param("identifier") String identifier);

    @Query("select product from Product product left join fetch product.categories ca where ca.identifier =:identifier")
    List<Product> findAllWithCategory(@Param("identifier") String identifier);

    @Modifying
    @Query("delete from Product  p where p.identifier=:identifier")
    void deleteByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    @Query("select p.id from Product p where p.identifier=:identifier")
    Long getProductId(@Param("identifier") String identifier);


}
