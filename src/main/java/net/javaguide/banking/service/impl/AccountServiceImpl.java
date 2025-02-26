package net.javaguide.banking.service.impl;

import org.springframework.stereotype.Service;

import net.javaguide.banking.dto.AccountDto;
import net.javaguide.banking.entity.Account;
import net.javaguide.banking.entity.User;
import net.javaguide.banking.mapper.AccountMapper;
import net.javaguide.banking.service.AccountService;
import net.javaguide.banking.utils.SecurityUtils;
import net.javaguide.banking.repository.AccountRepository;
import net.javaguide.banking.repository.UserRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        String username = SecurityUtils.getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (user.getId() == null) {
            throw new RuntimeException("User ID is null for user: " + username);
        }

        Account account = AccountMapper.mapToAccount(accountDto, user);
        System.out.println("Saving account with user_id: " + account.getUser().getId());

        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccount() {
        String username = SecurityUtils.getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Account account = accountRepository.findByAccountHolderName(user.getUsername());
        if (account == null) {
            throw new RuntimeException("Account not found for user: " + username);
        }
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountMapper.mapToAccountDto(account);
    }
}