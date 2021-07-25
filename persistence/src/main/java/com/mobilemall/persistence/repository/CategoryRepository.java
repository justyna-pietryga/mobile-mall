package com.mobilemall.persistence.repository;

import com.mobilemall.persistence.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
