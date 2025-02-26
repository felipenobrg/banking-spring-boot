package net.javaguide.banking.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguide.banking.entity.TransactionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private Long toAccountId;
    private Double amount;
    private TransactionType transactionType;
    private LocalDateTime transactionDate;
    private String toAccountName;

}