package com.company;

import java.io.IOException;

import java.util.Date;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        SabtAhval sabtAhval = new SabtAhval();

        int continueChooser;
        System.out.println("Welcome");
        do {

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
