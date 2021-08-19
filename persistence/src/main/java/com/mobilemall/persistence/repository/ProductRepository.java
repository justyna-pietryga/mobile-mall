package com.mobilemall.persistence.repository;

import com.mobilemall.persistence.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
