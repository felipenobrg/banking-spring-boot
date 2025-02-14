package net.javaguide.banking.service;

import net.javaguide.banking.dto.TransactionDto;

public interface TransactionService {
    TransactionDto deposit(Long accountId, Double amount);

    // TransactionDto withdraw(Long accountId, Double amount);

    // TransactionDto transfer(Long fromAccountId, Long toAccountId, Double amount);
}
