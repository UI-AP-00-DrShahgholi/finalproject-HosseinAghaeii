package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MYDate {
    Date date = new Date();
    int incrementDay = 1;
    SimpleDateFormat x = new SimpleDateFormat("yyyy-MM-dd");
    Scanner input = new Scanner(System.in);

    public void incrementDay(){
        date.setDate(date.getDate() + incrementDay);
        incrementDay++;
        showToday();
    }

    public void showToday() {
        System.out.println("today is: ");
        System.out.println(x.format(date));
    }
}
