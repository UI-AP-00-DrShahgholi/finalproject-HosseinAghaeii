package com.company;

import java.util.Date;

public class BankAccount {
    private String accountNumber;
    private String ownerNCode; // owner national code
    private int balance;
    private String buildDate;
    private int negativePoint=0;

    public BankAccount(String accountNumber, String ownerNCode, int balance, String buildDate) {
        this.accountNumber = accountNumber;
        this.ownerNCode = ownerNCode;
        this.balance = balance;
        this.buildDate = buildDate;

    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setOwnerNCode(String ownerNCode) {
        this.ownerNCode = ownerNCode;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public void setNegativePoint(int negativePoint) {
        this.negativePoint = negativePoint;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerNCode() {
        return ownerNCode;
    }

    public int getBalance() {
        return balance;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public int getNegativePoint() {
        return negativePoint;
    }
}
