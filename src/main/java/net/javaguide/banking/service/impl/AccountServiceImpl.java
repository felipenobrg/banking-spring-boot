package net.javaguide.banking.service.impl;

import org.springframework.stereotype.Service;

import net.javaguide.banking.dto.AccountDto;
import net.javaguide.banking.entity.Account;
import net.javaguide.banking.mapper.AccountMapper;
import net.javaguide.banking.service.AccountService;
import net.javaguide.banking.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountMapper.mapToAccountDto(account);
    }
}