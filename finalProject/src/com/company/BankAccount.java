package com.company;

public class BankAccount {
    private long accountNumber;
    private String ownerNCode; // owner national code
    private int balance;
    private String buildDate;
    private int negativePoint;

    public BankAccount(long accountNumber, String ownerNCode, int balance, String buildDate, int negativePoint) {
        this.accountNumber = accountNumber;
        this.ownerNCode = ownerNCode;
        this.balance = balance;
        this.buildDate = buildDate;
        this.negativePoint = negativePoint;
    }

    public void setAccountNumber(long accountNumber) {
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

    public long getAccountNumber() {
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
