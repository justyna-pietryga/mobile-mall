package com.mobilemall.persistence.repository;

import com.mobilemall.persistence.model.Category;
import com.mobilemall.persistence.model.StandardCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findCategoryByNameAndUrl(String originalName, String url);
    List<Category> findCategoriesByStandardCategory(StandardCategory standardCategory);
}
