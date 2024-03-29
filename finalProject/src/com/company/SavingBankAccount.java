package com.company;

import java.util.Date;

public class SavingBankAccount extends BankAccount{
    private int bankInterest;
    private type type;
    private int period;



    public SavingBankAccount(String accountNumber, String ownerNCode, int balance, String buildDate) {
        super(accountNumber, ownerNCode, balance, buildDate);


    }



    public enum type{
        SPECIAL,
        SHORT_TERM,
        LONG_TERM
    }

   public void setInterestAndPeriod(){
        if (type == type.SPECIAL){
            bankInterest=50;
            period=50;
        }
        if (type == type.SHORT_TERM){
            bankInterest=10;
            period=10;
        }
        if (type == type.LONG_TERM){
            bankInterest=30;
            period=30;
        }
    }

    public void setBankInterest(int bankInterest) {
        this.bankInterest = bankInterest;
    }

    public void setType(SavingBankAccount.type type) {
        this.type = type;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getBankInterest() {
        return bankInterest;
    }

    public SavingBankAccount.type getType() {
        return type;
    }

    public int getPeriod() {
        return period;
    }
}
