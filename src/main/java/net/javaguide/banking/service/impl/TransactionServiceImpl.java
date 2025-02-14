package net.javaguide.banking.service.impl;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import net.javaguide.banking.dto.TransactionDto;
import net.javaguide.banking.entity.Account;
import net.javaguide.banking.entity.Transaction;
import net.javaguide.banking.mapper.TransactionMapper;
import net.javaguide.banking.repository.AccountRepository;
import net.javaguide.banking.repository.TransactionRepository;
import net.javaguide.banking.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public TransactionDto deposit(Long accountId, TransactionDto transactionDto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (transactionDto.getAmount() < 0) {
            throw new RuntimeException("Amount cannot be negative");
        }

        System.out.println("TransactionDto" + transactionDto);

        account.setBalance(account.getBalance() + transactionDto.getAmount());
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setTransactionDate(transactionDto.getTransactionDate() != null
                ? transactionDto.getTransactionDate()
                : LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    public TransactionDto withdraw(Long accountId, TransactionDto transactionDto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (transactionDto.getAmount() < 0) {
            throw new RuntimeException("Amount cannot be negative");
        }

        System.out.println("TransactionDto" + transactionDto);

        account.setBalance(account.getBalance() - transactionDto.getAmount());
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setTransactionDate(transactionDto.getTransactionDate() != null
                ? transactionDto.getTransactionDate()
                : LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    // public void transfer(Long fromAccountId, Long toAccountId, Double amount) {
    // Account fromAccount = accountRepository.findById(fromAccountId)
    // .orElseThrow(() -> new RuntimeException("Account not found"));
    // Account toAccount = accountRepository.findById(toAccountId)
    // .orElseThrow(() -> new RuntimeException("Account not found"));
    // if (fromAccount.getBalance() < amount) {
    // throw new RuntimeException("Insufficient balance");
    // }
    // fromAccount.setBalance(fromAccount.getBalance() - amount);
    // toAccount.setBalance(toAccount.getBalance() + amount);
    // accountRepository.save(fromAccount);
    // accountRepository.save(toAccount);
    // Transaction transaction = new Transaction();
    // transaction.setAccount(fromAccount);
    // transaction.setAmount(amount);
    // transaction.setType(TransactionType.TRANSFER);
    // transaction.setToAccount(toAccount);
    // transactionRepository.save(transaction);
    // }
}
