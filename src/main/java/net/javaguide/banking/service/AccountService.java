package net.javaguide.banking.service;

import net.javaguide.banking.dto.AccountDto;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);
}