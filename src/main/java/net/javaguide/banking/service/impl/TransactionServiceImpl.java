package net.javaguide.banking.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import net.javaguide.banking.dto.TransactionDto;
import net.javaguide.banking.entity.Account;
import net.javaguide.banking.entity.Transaction;
import net.javaguide.banking.entity.TransactionType;
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

    public List<TransactionDto> getHistoric() {
        String username = SecurityUtils.getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (user.getId() == null) {
            throw new RuntimeException("User ID is null for user: " + username);
        }

        Account account = accountRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<Transaction> transactionHistoric = transactionRepository.findByAccountId(account.getId());
        return transactionHistoric.stream()
                .map(TransactionMapper::mapToTransactionDto)
                .toList();
    }

    public TransactionDto transfer(String accountHolderName, Double amount) {
        String username = SecurityUtils.getAuthenticatedUsername();
        if (username == null) {
            throw new RuntimeException("Usuário não autenticado");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (user.getId() == null) {
            throw new RuntimeException("User ID is null for user: " + username);
        }

        Account fromAccount = accountRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account toAccount = accountRepository.findByAccountHolderName(accountHolderName);
        if (toAccount == null) {
            throw new RuntimeException("Recipient account not found: " + accountHolderName);
        }

        if (amount == null || amount <= 0) {
            throw new RuntimeException("Transfer amount must be greater than zero");
        }

        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance for transfer");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setAccount(fromAccount);
        transaction.setToAccountId(toAccount.getId());
        transaction.setToAccountName(toAccount.getAccountHolderName());
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTransactionDate(LocalDateTime.now());
        System.out.println(transaction.getAmount());
        System.out.println(transaction.getTransactionType());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

}
