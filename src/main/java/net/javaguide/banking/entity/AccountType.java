package net.javaguide.banking.entity;

public enum AccountType {
    SAVINGS,
    CHECKING,
    LOAN;

    public static AccountType fromString(String accountType) {
        for (AccountType type : AccountType.values()) {
            if (type.name().equalsIgnoreCase(accountType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid account type: " + accountType);
    }
}