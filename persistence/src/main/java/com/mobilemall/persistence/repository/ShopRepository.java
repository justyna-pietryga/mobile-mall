package com.mobilemall.persistence.repository;

import com.mobilemall.persistence.model.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends CrudRepository<Shop, String> {
}
