package net.javaguide.banking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguide.banking.dto.AccountDto;
import net.javaguide.banking.service.AccountService;

@RestController()
@RequestMapping("api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") Long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    // @PostMapping("/{id}/deposit/{amount}")
    // public ResponseEntity<AccountDto> deposit(@PathVariable("id") Long id,
    // @PathVariable("amount") Double amount) {
    // AccountDto accountDto = accountService.deposit(id, amount);
    // return ResponseEntity.ok(accountDto);
    // }

    // @PostMapping("/{id}/withdraw/{amount}")
    // public ResponseEntity<AccountDto> withdraw(@PathVariable("id") Long id,
    // @PathVariable("amount") Double amount) {
    // AccountDto accountDto = accountService.withdraw(id, amount);
    // return ResponseEntity.ok(accountDto);
    // }

}
