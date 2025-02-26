package net.javaguide.banking.entity;

public enum TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER;

    public static TransactionType fromString(String transactionType) {
        for (TransactionType type : TransactionType.values()) {
            if (type.name().equalsIgnoreCase(transactionType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid account type: " + transactionType);
    }
}