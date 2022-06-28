package com.company;

import java.io.IOException;

import java.util.Scanner;

import java.sql.*;



public class Main {

    public static void main(String[] args) throws Exception {

        Scanner input=new Scanner(System.in);
        SabtAhval sabtAhval=new SabtAhval();
        System.out.println("Welcome, who are you?1.Admin\t2.User");
        int chooser=input.nextInt();
        switch (chooser){
            case 1: sabtAhval.menu();
            break;
            case 2:
        }

    }
}
