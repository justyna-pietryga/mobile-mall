package com.mobilemall.persistence.repository;

import com.mobilemall.persistence.model.MallOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MallOrderRepository extends CrudRepository<MallOrder, Long> {
    Iterable<MallOrder> findMallOrdersByMallUser_Email(String email);
}
