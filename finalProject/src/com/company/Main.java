package com.company;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
          MYDate myDate=new MYDate();
        Scanner input = new Scanner(System.in);
        SabtAhval sabtAhval = new SabtAhval();
        SQLConnection sqlConnection=new SQLConnection();

        int continueChooser;
        System.out.println("Welcome");
        do {
            System.out.println("Do you want increment day?\t1.yes\t2.NO");
            int iChooser=input.nextInt();
            if (iChooser==1){
                System.out.println("inter saving bank account number for check interest");
                String ANumber =input.next();
                System.out.println("inter national code");
                String nCode=input.next();
                if (sqlConnection.checkANumber(ANumber,3,nCode)) {
                    myDate.incrementDay(ANumber);
                }else System.out.println("wrong information");
            }

            System.out.println("who are you?1.Admin\t2.User");
            int chooser = input.nextInt();
            switch (chooser) {
                case 1:
                    sabtAhval.menu();
                    break;
                case 2:
                    userMenu();
            }
            System.out.println("Do you want Resume?\t1.yes\t2.no");
             continueChooser = input.nextInt();

        } while (continueChooser==1);
    }

    public static void userMenu() throws Exception {
        SabtAsnad sabtAsnad = new SabtAsnad();
        BankSystem bankSystem=new BankSystem();
        Scanner input=new Scanner(System.in);
        System.out.println("Which system do you want to log in to?");
        System.out.println("1.Sabt Asnad system");
        System.out.println("2.Bank system");
        int chooser=input.nextInt();
        switch (chooser){
            case 1: sabtAsnad.login();
            break;
            case 2: bankSystem.login();

        }
    }
}
