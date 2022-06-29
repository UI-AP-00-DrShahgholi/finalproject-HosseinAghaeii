package com.company;

import java.io.IOException;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        SabtAhval sabtAhval = new SabtAhval();
        SabtAsnad sabtAsnad = new SabtAsnad();
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
                    sabtAsnad.login();
            }
            System.out.println("Do you want Resume?\t1.yes\t2.no");
             continueChooser = input.nextInt();

        } while (continueChooser==1);
    }
}
