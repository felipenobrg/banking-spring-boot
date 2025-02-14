package net.javaguide.banking.service;

import net.javaguide.banking.dto.TransactionDto;

public interface TransactionService {
    TransactionDto deposit(Long accountId, TransactionDto transactionDto);

    TransactionDto withdraw(Long accountId, TransactionDto transactionDto);

    // TransactionDto transfer(Long fromAccountId, Long toAccountId, Double amount);
}
