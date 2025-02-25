package net.javaguide.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguide.banking.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
}
