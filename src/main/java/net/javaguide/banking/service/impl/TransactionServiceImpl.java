package net.javaguide.banking.service.impl;

import org.springframework.stereotype.Service;

import net.javaguide.banking.dto.TransactionDto;
import net.javaguide.banking.entity.Account;
import net.javaguide.banking.entity.Transaction;
import net.javaguide.banking.entity.TransactionType;
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

    public TransactionDto deposit(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAccountId(accountId);
        transactionDto.setAmount(amount);
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto);
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.DEPOSIT);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    // public void withdraw(Long accountId, Double amount) {
    // Account account = accountRepository.findById(accountId)
    // .orElseThrow(() -> new RuntimeException("Account not found"));
    // if (account.getBalance() < amount) {
    // throw new RuntimeException("Insufficient balance");
    // }
    // account.setBalance(account.getBalance() - amount);
    // accountRepository.save(account);
    // Transaction transaction = new Transaction();
    // transaction.setAccount(account);
    // transaction.setAmount(amount);
    // transaction.setType(TransactionType.WITHDRAW);
    // transactionRepository.save(transaction);
    // }

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
