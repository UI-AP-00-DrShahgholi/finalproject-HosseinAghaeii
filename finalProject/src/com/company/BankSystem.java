package com.company;

import java.util.Date;
import java.util.Scanner;

public class BankSystem implements Login {
    Scanner input = new Scanner(System.in);
    SQLConnection sqlConnection = new SQLConnection();
    private String nationalCode;

    public BankSystem() throws Exception {
    }

    @Override
    public void login() throws Exception {
        System.out.println("inter your national code:");
        String NCode = input.next();
        if (sqlConnection.checkNCode(NCode)) {
            String name = sqlConnection.findName(NCode);
            System.out.println("Welcome dear " + name + ":)");
            nationalCode = NCode;
            menu();
        } else System.out.println("we not have person with this national code");
    }

    private void menu() {
        System.out.println("What do you want to do?");
        System.out.println("1.Create account ");
        System.out.println("2.Push money");//واریز وجه
        System.out.println("3.pop  money");//دریافت وجه
        System.out.println("4.Get a bank card");
        System.out.println("5.Get a batch of checks");
        System.out.println("6.Pass the check");
        System.out.println("7.transfer money");
        int chooser = input.nextInt();
        switch (chooser) {
            case 1:
                createAccount();
                break;
            case 2:
                pushMoney();
                break;
            case 3:
                popMoney();
                break;
            case 4:
                getBankCard();
                break;
            case 5:
                getBathOfCheck();
                break;
            case 6:
                passCheck();
                break;
            case 7:
                   transferMoney();
                   break;
        }
    }

    private void createAccount() {
        System.out.println("which type of account do yuo want to crate?");
        System.out.println("1.current account");
        System.out.println("2.GHARZOLHASANE account");
        System.out.println("3.saving account");
        int chooser=input.nextInt();
        switch (chooser){
            case 1: createCAccount();
            break;
            case 2: createGHAccount();
            break;
            case 3: createSAccount();
        }

    }

    private void createCAccount(){
        System.out.println("inter information we want:");
        System.out.println("account number, balance, card number,build date");
        System.out.println("notice! account number and card number must be unique");
        String ANumber=input.next();
        int balance =input.nextInt();
        String cardNumber=input.next();
        String buildDate=input.next();
        CurrentBankAccount CAccount=new CurrentBankAccount(ANumber,nationalCode,balance,buildDate,cardNumber);
        String SQlCmd=String.format("INSERT INTO CAccount (ANumber,ownerNCode,balance,buildDate,NPoint,checkBook,bankCard,cardNumber) VALUE ('%s','%s',%d,'%s',%d,%d,%d,'%s')",
                CAccount.getAccountNumber(),CAccount.getOwnerNCode(),CAccount.getBalance(),CAccount.getBuildDate(),CAccount.getNegativePoint(),CAccount.getCheckbook(),CAccount.getBankCard(),CAccount.getCardNumber());
        if (sqlConnection.executeSQL(SQlCmd)){
            System.out.println("your current account created");
        }else System.out.println("ERROR!");
    }

    private void createGHAccount(){
        System.out.println("inter information we want:");
        System.out.println("account number, balance, card number,build date");
        System.out.println("notice! account number and card number must be unique");
        String ANumber=input.next();
        int balance =input.nextInt();
        String cardNumber=input.next();
        String buildDate=input.next();
        GhHBankAccount ghHBankAccount=new GhHBankAccount(ANumber,nationalCode,balance,buildDate,cardNumber);
        String SQlCmd=String.format("INSERT INTO GHAccount (ANumber,ownerNCode,balance,buildDate,NPoint,bankCard,cardNumber) VALUE ('%s','%s',%d,'%s',%d,%d,'%s')",
                ghHBankAccount.getAccountNumber(),ghHBankAccount.getOwnerNCode(),ghHBankAccount.getBalance(),ghHBankAccount.getBuildDate(),ghHBankAccount.getNegativePoint(),ghHBankAccount.getBankCard(),ghHBankAccount.getCardNumber());
        if (sqlConnection.executeSQL(SQlCmd)){
            System.out.println("your gharzolhasane account created");
        }else System.out.println("ERROR!");

    }

    private void createSAccount(){
        System.out.println("inter information we want:");
        System.out.println("account number, balance, build date");
        System.out.println("notice! account number and card number must be unique");
        String ANumber=input.next();
        int balance =input.nextInt();

        String buildDate=input.next();
        System.out.println("inter type of saving account:");
        System.out.println("1.SPECIAL");
        System.out.println("2.short term");
        System.out.println("3.long term");
        SavingBankAccount savingBankAccount=new SavingBankAccount(ANumber,nationalCode,balance,buildDate);
        int chooser=input.nextInt();
        switch (chooser){
            case 1: savingBankAccount.setType(SavingBankAccount.type.SPECIAL);
            break;
            case  2: savingBankAccount.setType(SavingBankAccount.type.SHORT_TERM);
            break;
            case 3: savingBankAccount.setType(SavingBankAccount.type.LONG_TERM);
            break;
        }
        savingBankAccount.setInterestAndPeriod();
        String SQlCmd=String.format("INSERT INTO SAccount (ANumber,ownerNCode,balance,buildDate,NPoint,type,bankInterest,period) VALUE ('%s','%s',%d,'%s',%d,'%s',%d,%d)",
                savingBankAccount.getAccountNumber(),savingBankAccount.getOwnerNCode(),savingBankAccount.getBalance(),savingBankAccount.getBuildDate(),savingBankAccount.getNegativePoint(),savingBankAccount.getType(),savingBankAccount.getBankInterest(),savingBankAccount.getPeriod());
        if (sqlConnection.executeSQL(SQlCmd)){
            System.out.println("your saving account created");
        }else System.out.println("ERROR!");
    }

    private void pushMoney() {

    }

    private void popMoney() {

    }

    private void getBankCard() {

    }

    private void getBathOfCheck() {

    }

    private void passCheck() {

    }

    private void transferMoney() {

    }
}
