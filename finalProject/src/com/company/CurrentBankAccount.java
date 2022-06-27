package com.company;

public class CurrentBankAccount extends BankAccount{
    private boolean checkbook;
    private boolean bankCard;
    private long cardNumber;


    public CurrentBankAccount(long accountNumber, String ownerNCode, int balance, String buildDate, int negativePoint,boolean checkbook,boolean bankCard) {
        super(accountNumber, ownerNCode, balance, buildDate, negativePoint);
        this.checkbook=checkbook;
        this.bankCard=bankCard;
    }

    public boolean isCheckbook() {
        return checkbook;
    }

    public boolean isBankCard() {
        return bankCard;
    }

    public void setCheckbook(boolean checkbook) {
        this.checkbook = checkbook;
    }

    public void setBankCard(boolean bankCard) {
        this.bankCard = bankCard;
    }
}
