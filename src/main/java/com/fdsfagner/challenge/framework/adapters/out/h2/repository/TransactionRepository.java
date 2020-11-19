package com.fdsfagner.challenge.framework.adapters.out.h2.repository;

import com.fdsfagner.challenge.framework.adapters.out.h2.entity.AccountEntity;
import com.fdsfagner.challenge.framework.adapters.out.h2.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findById(Long id);
}
