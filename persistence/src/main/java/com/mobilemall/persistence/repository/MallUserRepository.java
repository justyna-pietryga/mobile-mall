package com.mobilemall.persistence.repository;

import com.mobilemall.persistence.model.MallUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MallUserRepository extends CrudRepository<MallUser, Long> {
    Optional<MallUser> findByEmail(String email);
}
