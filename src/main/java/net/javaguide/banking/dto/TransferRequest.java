package net.javaguide.banking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    private String accountHolderName;
    private Double amount;

}
