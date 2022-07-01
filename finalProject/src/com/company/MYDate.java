package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MYDate {
    BankSystem bankSystem=new BankSystem();
    Date date = new Date();
    int incrementDay = 1;
    SimpleDateFormat x = new SimpleDateFormat("yyyy-MM-dd");
    Scanner input = new Scanner(System.in);

    public MYDate() throws Exception {
    }

    public void incrementDay() {
        try {
        bankSystem.passVam();
        }catch (NotEnoughMoneyException ex){
            System.out.println(ex.getMessage());
        }
        catch (Exception e){
            System.out.println("ERROR in work with phpAdmin");
        }
        date.setDate(date.getDate() + incrementDay);
        incrementDay++;
        showToday();

    }

    public void showToday() {
        System.out.println("today is: ");
        System.out.println(x.format(date));
    }
}
