package com.company;

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
