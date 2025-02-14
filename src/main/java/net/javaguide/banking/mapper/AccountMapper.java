package net.javaguide.banking.mapper;

import net.javaguide.banking.dto.AccountDto;
import net.javaguide.banking.entity.Account;
import net.javaguide.banking.entity.AccountType;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto) {
        Account account = new Account();

        account.setId(accountDto.getId());
        account.setBalance(accountDto.getBalance());
        account.setAccountHolderName(accountDto.getAccountHolderName());
        account.setCurrency(accountDto.getCurrency());
        account.setOverdraftLimit(accountDto.getOverdraftLimit());

        // Convert String to Enum
        if (accountDto.getAccountType() != null) {
            try {
                account.setAccountType(AccountType.valueOf(accountDto.getAccountType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid account type: " + accountDto.getAccountType());
            }
        }

        return account;
    }

    public static AccountDto mapToAccountDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance(),
                account.getAccountType() != null ? account.getAccountType().toString() : null,
                account.getCurrency(),
                account.getOverdraftLimit());
    }
}
