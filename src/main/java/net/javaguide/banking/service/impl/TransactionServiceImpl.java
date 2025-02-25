package net.javaguide.banking.service.impl;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import net.javaguide.banking.dto.TransactionDto;
import net.javaguide.banking.entity.Account;
import net.javaguide.banking.entity.Transaction;
import net.javaguide.banking.entity.User;
import net.javaguide.banking.mapper.TransactionMapper;
import net.javaguide.banking.repository.AccountRepository;
import net.javaguide.banking.repository.TransactionRepository;
import net.javaguide.banking.repository.UserRepository;
import net.javaguide.banking.service.TransactionService;
import net.javaguide.banking.utils.SecurityUtils;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository,
            UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public TransactionDto deposit(TransactionDto transactionDto) {
        String username = SecurityUtils.getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (user.getId() == null) {
            throw new RuntimeException("User ID is null for user: " + username);
        }

        Account account = accountRepository.findById(user.getId())
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

    public TransactionDto withdraw(TransactionDto transactionDto) {
        String username = SecurityUtils.getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (user.getId() == null) {
            throw new RuntimeException("User ID is null for user: " + username);
        }

        Account account = accountRepository.findById(user.getId())
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
