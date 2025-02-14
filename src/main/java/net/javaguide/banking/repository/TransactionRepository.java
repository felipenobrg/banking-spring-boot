package net.javaguide.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguide.banking.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
