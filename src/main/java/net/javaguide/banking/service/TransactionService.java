package net.javaguide.banking.service;

import java.util.List;

import net.javaguide.banking.dto.TransactionDto;

public interface TransactionService {
    TransactionDto deposit(TransactionDto transactionDto);

    TransactionDto withdraw(TransactionDto transactionDto);

    List<TransactionDto> getHistoric();

    TransactionDto transfer(String accountHolderName, Double amount);
}
