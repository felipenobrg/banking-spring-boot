package net.javaguide.banking.service;

import net.javaguide.banking.dto.TransactionDto;

public interface TransactionService {
    TransactionDto deposit(TransactionDto transactionDto);

    TransactionDto withdraw(TransactionDto transactionDto);

    // TransactionDto transfer(Long fromAccountId, Long toAccountId, Double amount);
}
