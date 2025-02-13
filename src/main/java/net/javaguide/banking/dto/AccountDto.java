package net.javaguide.banking.dto;

import lombok.Data;

@Data()
public class AccountDto {
    private Long id;
    private String accountHolderName;
    private Double balance;

}
