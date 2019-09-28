package com.adcash.challenge.repository;
import com.adcash.challenge.domain.Category;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByIdentifierEquals(String identifier);

    @Modifying
    void deleteByIdentifier(String identifier);

    Category findOneByIdentifier(String identifier);

    @Modifying
    @Query("update Category  set name=:name where identifier=:identifier")
    void update(String identifier, String name);

}
