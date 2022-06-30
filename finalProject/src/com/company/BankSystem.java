package com.company;

import java.sql.SQLException;
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

    private void menu() throws Exception {
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
        int chooser = input.nextInt();
        switch (chooser) {
            case 1:
                createCAccount();
                break;
            case 2:
                createGHAccount();
                break;
            case 3:
                createSAccount();
        }

    }

    private void createCAccount() {
        System.out.println("inter information we want:");
        System.out.println("account number, balance, card number,build date");
        System.out.println("notice! account number and card number must be unique");
        String ANumber = input.next();
        int balance = input.nextInt();
        String cardNumber = input.next();
        String buildDate = input.next();
        CurrentBankAccount CAccount = new CurrentBankAccount(ANumber, nationalCode, balance, buildDate, cardNumber);
        String SQlCmd = String.format("INSERT INTO CAccount (ANumber,ownerNCode,balance,buildDate,NPoint,checkBook,bankCard,cardNumber) VALUE ('%s','%s',%d,'%s',%d,%d,%d,'%s')",
                CAccount.getAccountNumber(), CAccount.getOwnerNCode(), CAccount.getBalance(), CAccount.getBuildDate(), CAccount.getNegativePoint(), CAccount.getCheckbook(), CAccount.getBankCard(), CAccount.getCardNumber());
        if (sqlConnection.executeSQL(SQlCmd)) {
            System.out.println("your current account created");
        } else System.out.println("ERROR!");
    }

    private void createGHAccount() {
        System.out.println("inter information we want:");
        System.out.println("account number, balance, card number,build date");
        System.out.println("notice! account number and card number must be unique");
        String ANumber = input.next();
        int balance = input.nextInt();
        String cardNumber = input.next();
        String buildDate = input.next();
        GhHBankAccount ghHBankAccount = new GhHBankAccount(ANumber, nationalCode, balance, buildDate, cardNumber);
        String SQlCmd = String.format("INSERT INTO GHAccount (ANumber,ownerNCode,balance,buildDate,NPoint,bankCard,cardNumber) VALUE ('%s','%s',%d,'%s',%d,%d,'%s')",
                ghHBankAccount.getAccountNumber(), ghHBankAccount.getOwnerNCode(), ghHBankAccount.getBalance(), ghHBankAccount.getBuildDate(), ghHBankAccount.getNegativePoint(), ghHBankAccount.getBankCard(), ghHBankAccount.getCardNumber());
        if (sqlConnection.executeSQL(SQlCmd)) {
            System.out.println("your gharzolhasane account created");
        } else System.out.println("ERROR!");

    }

    private void createSAccount() {
        System.out.println("inter information we want:");
        System.out.println("account number, balance, build date");
        System.out.println("notice! account number and card number must be unique");
        String ANumber = input.next();
        int balance = input.nextInt();

        String buildDate = input.next();
        System.out.println("inter type of saving account:");
        System.out.println("1.SPECIAL");
        System.out.println("2.short term");
        System.out.println("3.long term");
        SavingBankAccount savingBankAccount = new SavingBankAccount(ANumber, nationalCode, balance, buildDate);
        int chooser = input.nextInt();
        switch (chooser) {
            case 1:
                savingBankAccount.setType(SavingBankAccount.type.SPECIAL);
                break;
            case 2:
                savingBankAccount.setType(SavingBankAccount.type.SHORT_TERM);
                break;
            case 3:
                savingBankAccount.setType(SavingBankAccount.type.LONG_TERM);
                break;
        }
        savingBankAccount.setInterestAndPeriod();
        String SQlCmd = String.format("INSERT INTO SAccount (ANumber,ownerNCode,balance,buildDate,NPoint,type,bankInterest,period) VALUE ('%s','%s',%d,'%s',%d,'%s',%d,%d)",
                savingBankAccount.getAccountNumber(), savingBankAccount.getOwnerNCode(), savingBankAccount.getBalance(), savingBankAccount.getBuildDate(), savingBankAccount.getNegativePoint(), savingBankAccount.getType(), savingBankAccount.getBankInterest(), savingBankAccount.getPeriod());
        if (sqlConnection.executeSQL(SQlCmd)) {
            System.out.println("your saving account created");
        } else System.out.println("ERROR!");
    }

    private void pushMoney() throws SQLException {
        System.out.println("inter type of account:");
        System.out.println("1.current account");
        System.out.println("2.GHARZOLHASANE account");
        System.out.println("3.saving account");
        int choose = input.nextInt();
        System.out.println("inter your account number:");
        String ANumber = input.next();
        if (sqlConnection.checkANumber(ANumber, choose)) {
            System.out.println("How much money do you want to deposit?");
            int money = input.nextInt();

            switch (choose) {
                case 1:
                    pushMoneyForCA(ANumber, money);
                    break;
                case 2:
                    pushMoneyForGHA(ANumber, money);
                    break;
                case 3:
                    pushMoneyForSA(ANumber, money);
                    break;
            }
        } else
            System.out.println("wrong account number :(");

    }

    private void pushMoneyForCA(String ANumber, int money) throws SQLException {
        int balance = sqlConnection.findBalance(ANumber, 1) + money;
        String SQLCmd = String.format("UPDATE CAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("push money is complete");
        } else System.out.println("ERROR : push money is not complete");
    }

    private void pushMoneyForGHA(String ANumber, int money) throws SQLException {
        int balance = sqlConnection.findBalance(ANumber, 2) + money;
        String SQLCmd = String.format("UPDATE GHAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("push money is complete");
        } else System.out.println("ERROR : push money is not complete");
    }

    private void pushMoneyForSA(String ANumber, int money) throws SQLException {
        int balance = sqlConnection.findBalance(ANumber, 3) + money;
        String SQLCmd = String.format("UPDATE SAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("push money is complete");
        } else System.out.println("ERROR : push money is not complete");
    }

    private void popMoney() throws Exception {
        System.out.println("inter type of account:");
        System.out.println("1.current account");
        System.out.println("2.GHARZOLHASANE account");
        System.out.println("3.saving account");
        int choose = input.nextInt();
        System.out.println("inter your account number:");
        String ANumber = input.next();
        if (sqlConnection.checkANumber(ANumber, choose)) {
            System.out.println("How much money do you want to withdraw?");
            int money = input.nextInt();
            if (money <= sqlConnection.findBalance(ANumber, choose)) {
                switch (choose) {
                    case 1:
                        popMoneyForCA(ANumber,money);
                        break;
                    case 2:
                        popMoneyForGHA(ANumber, money);
                        break;
                    case 3:
                        popMoneyForSA(ANumber, money);
                        break;
                }
            }else System.out.println("Your balance is not enough");
        } else
            System.out.println("wrong account number :(");
    }

    private void popMoneyForCA(String ANumber, int money) throws Exception {
        int balance = sqlConnection.findBalance(ANumber, 1) - money;
        pushToWallet(money);
        String SQLCmd = String.format("UPDATE CAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("pop money is complete");
        } else System.out.println("ERROR : pup money is not complete");
    }

    private void popMoneyForGHA(String ANumber, int money) throws Exception {
        int balance = sqlConnection.findBalance(ANumber, 2) - money;
        pushToWallet(money);
        String SQLCmd = String.format("UPDATE GHAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("pop money is complete");
        } else System.out.println("ERROR : pup money is not complete");
    }

    private void popMoneyForSA(String ANumber, int money) throws Exception {
        int balance = sqlConnection.findBalance(ANumber, 3) - money;
        pushToWallet(money);
        String SQLCmd = String.format("UPDATE SAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("pop money is complete");
        } else System.out.println("ERROR : pup money is not complete");
    }



    private void pushToWallet(int money) throws Exception {
        int walletMount=sqlConnection.getWallet(nationalCode)+money;
        String SQLCmd =String.format("UPDATE User SET walletMount = %d WHERE nationalCode = '%s'",walletMount,nationalCode);
        if (sqlConnection.executeSQL(SQLCmd)){
            System.out.println("wallet mount update");
        }else System.out.println("ERROR : in transfer money to wallet ");
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
