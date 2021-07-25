package com.mobilemall.persistence.repository;

import com.mobilemall.persistence.model.StandardCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StandardCategoryRepository extends CrudRepository<StandardCategory, Long> {
    Optional<StandardCategory> findStandardCategoryByName(String name);
}
