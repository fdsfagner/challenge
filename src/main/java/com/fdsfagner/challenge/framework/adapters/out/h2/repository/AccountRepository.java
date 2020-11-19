package com.fdsfagner.challenge.framework.adapters.out.h2.repository;

import com.fdsfagner.challenge.framework.adapters.out.h2.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findById(Long id);
}
