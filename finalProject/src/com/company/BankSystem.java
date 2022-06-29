package com.company;

import java.util.Scanner;

public class BankSystem implements Login{
    Scanner input=new Scanner(System.in);
    SQLConnection sqlConnection=new SQLConnection();
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
            nationalCode=NCode;
            menu();
        } else System.out.println("we not have person with this national code");
    }

    private void menu(){

    }
}
