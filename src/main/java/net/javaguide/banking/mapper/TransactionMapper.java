package net.javaguide.banking.mapper;

import net.javaguide.banking.dto.TransactionDto;
import net.javaguide.banking.entity.Transaction;

public class TransactionMapper {

    public static Transaction mapToTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setTransactionDate(transactionDto.getTransactionDate());

        return transaction;
    }

    public static TransactionDto mapToTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setTransactionType(transaction.getTransactionType());
        transactionDto.setTransactionDate(transaction.getTransactionDate());
        transactionDto.setToAccountId(transaction.getToAccountId());
        transactionDto.setToAccountName(transaction.getToAccountName());
        return transactionDto;
    }
}
