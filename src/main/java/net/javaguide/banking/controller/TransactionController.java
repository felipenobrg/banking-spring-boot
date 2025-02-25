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

    private TransactionService transactionService;

    public TransactionController(TransactionService TransactionService) {
        this.transactionService = TransactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionDto> deposit(@RequestBody TransactionDto transactionDto) {
        System.out.println("TransactionDto: " + transactionDto);
        TransactionDto result = transactionService.deposit(transactionDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<TransactionDto> withdraw(@RequestBody TransactionDto transactionDto) {
        System.out.println("TransactionDto: " + transactionDto);
        TransactionDto result = transactionService.withdraw(transactionDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
