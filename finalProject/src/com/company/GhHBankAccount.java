package com.company;

import java.util.Date;

public class GhHBankAccount extends BankAccount{
    private boolean bankCard;
    private long cardNumber;

    public GhHBankAccount(String accountNumber, String ownerNCode, int balance, String buildDate, int negativePoint) {
        super(accountNumber, ownerNCode, balance, buildDate);
    }

    public void setBankCard(boolean bankCard) {
        this.bankCard = bankCard;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isBankCard() {
        return bankCard;
    }

    public long getCardNumber() {
        return cardNumber;
    }
}
