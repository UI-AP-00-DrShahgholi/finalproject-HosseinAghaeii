package com.company;

import java.util.Date;

public class GhHBankAccount extends BankAccount {
    private int bankCard = 1;
    private String cardNumber;

    public GhHBankAccount(String accountNumber, String ownerNCode, int balance, String buildDate,String cardNumber) {
        super(accountNumber, ownerNCode, balance, buildDate);
        this.cardNumber=cardNumber;
    }

    public int getBankCard() {
        return bankCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setBankCard(int bankCard) {
        this.bankCard = bankCard;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
