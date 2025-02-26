package net.javaguide.banking.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TransactionDto deposit(TransactionDto transactionDto) {
        validateAmount(transactionDto.getAmount());

        Account account = getCurrentUserAccount();

        account.setBalance(account.getBalance() + transactionDto.getAmount());
        accountRepository.save(account);

        Transaction transaction = createTransaction(
                account,
                transactionDto.getAmount(),
                transactionDto.getTransactionType(),
                transactionDto.getTransactionDate());

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Deposit of {} completed for account ID: {}", transactionDto.getAmount(), account.getId());

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    @Override
    @Transactional
    public TransactionDto withdraw(TransactionDto transactionDto) {
        validateAmount(transactionDto.getAmount());

        Account account = getCurrentUserAccount();

        if (account.getBalance() < transactionDto.getAmount()) {
            throw new RuntimeException("Insufficient balance for withdrawal");
        }

        account.setBalance(account.getBalance() - transactionDto.getAmount());
        accountRepository.save(account);

        Transaction transaction = createTransaction(
                account,
                transactionDto.getAmount(),
                transactionDto.getTransactionType(),
                transactionDto.getTransactionDate());

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Withdrawal of {} completed for account ID: {}", transactionDto.getAmount(), account.getId());

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    @Override
    public List<TransactionDto> getHistoric() {
        Account account = getCurrentUserAccount();

        List<Transaction> transactionHistoric = transactionRepository.findByAccountId(account.getId());
        return transactionHistoric.stream()
                .map(TransactionMapper::mapToTransactionDto)
                .toList();
    }

    @Override
    @Transactional
    public TransactionDto transfer(String accountHolderName, Double amount) {
        validateAmount(amount);

        Account fromAccount = getCurrentUserAccount();

        Account toAccount = accountRepository.findByAccountHolderName(accountHolderName);
        if (toAccount == null) {
            throw new RuntimeException("Recipient account not found: " + accountHolderName);
        }

        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new RuntimeException("Cannot transfer to the same account");
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

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transfer of {} from account ID: {} to account ID: {} completed",
                amount, fromAccount.getId(), toAccount.getId());

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    private Account getCurrentUserAccount() {
        String username = SecurityUtils.getAuthenticatedUsername();
        if (username == null) {
            throw new SecurityException("User not authenticated");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (user.getId() == null) {
            throw new RuntimeException("User ID is null for user: " + username);
        }

        return accountRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found for user: " + username));
    }

    private void validateAmount(Double amount) {
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }
    }

    private Transaction createTransaction(Account account, Double amount, TransactionType type, LocalDateTime date) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(type != null ? type : TransactionType.DEPOSIT);
        transaction.setTransactionDate(date != null ? date : LocalDateTime.now());
        return transaction;
    }
}