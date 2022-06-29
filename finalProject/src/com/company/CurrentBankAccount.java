package com.company;

import java.util.Date;

public class CurrentBankAccount extends BankAccount{
    private int  checkbook=1;
    private int bankCard=1;
    private String cardNumber;


    public CurrentBankAccount(String accountNumber, String ownerNCode, int balance, String buildDate, String cardNumber) {
        super(accountNumber, ownerNCode, balance, buildDate);
       this.cardNumber=cardNumber;
    }

    public int getCheckbook() {
        return checkbook;
    }

    public int getBankCard() {
        return bankCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
