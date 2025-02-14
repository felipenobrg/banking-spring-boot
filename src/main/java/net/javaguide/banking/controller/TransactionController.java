package net.javaguide.banking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguide.banking.dto.TransactionDto;
import net.javaguide.banking.service.TransactionService;

@RestController()
@RequestMapping("api/transactions")
public class TransactionController {

    private TransactionService TransactionService;

    public TransactionController(TransactionService TransactionService) {
        this.TransactionService = TransactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionDto> deposit(@RequestBody TransactionDto TransactionDto) {
        TransactionDto transactionDto = TransactionService.deposit(TransactionDto.getAccountId(),
                TransactionDto.getAmount());
        return new ResponseEntity<>(transactionDto, HttpStatus.CREATED);
    }
}
