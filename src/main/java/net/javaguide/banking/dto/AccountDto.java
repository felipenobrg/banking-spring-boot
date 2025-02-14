package net.javaguide.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String accountHolderName;
    private Double balance;
    private String accountType;
    private String currency;
    private Double overdraftLimit;
}
