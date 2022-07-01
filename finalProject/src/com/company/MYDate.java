package com.company;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MYDate {
    BankSystem bankSystem=new BankSystem();
    SQLConnection sqlConnection=new SQLConnection();
    Date date = new Date();
    int incrementDay = 1;
    SimpleDateFormat x = new SimpleDateFormat("yyyy-MM-dd");
    Scanner input = new Scanner(System.in);

    public MYDate() throws Exception {
    }

    public void incrementDay(String ANumber) throws SQLException {
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

        addInterest1(ANumber);
    }
    private void addInterest1(String ANumber) throws SQLException {
        Date date1;
        date1 = sqlConnection.findBuildDate(ANumber);
        int period = sqlConnection.findPeriod(ANumber);

        if (date.getDate() - date1.getDate() >= period) {
            addInterest(ANumber);
        } else System.out.println("interest dont add to balance  ");
    }
    private void addInterest(String ANumber) throws SQLException {
        int interest = sqlConnection.findInterest(ANumber);
        int balance = sqlConnection.findBalance(ANumber, 3);
        int newBalance = balance + ((interest / 100) * balance);
        String SQLCmd1 = String.format("UPDATE SAccount SET balance = %d WHERE ANumber = '%s'", newBalance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd1)) {
            System.out.println("HOORAY! interest of balance added to your balance.\t interest = " + ((interest / 100) * balance));
            String SQLCmd2 = String.format("UPDATE SAccount SET bankInterest = %d WHERE ANumber = '%s'", 0, ANumber);
            if (sqlConnection.executeSQL(SQLCmd2)) {
                System.out.println("bank interest chang to 0");
            }else System.out.println("ERROR in bank interest chang to 0");

        } else System.out.println("ERROR : in add interest");

    }

    public void showToday() {
        System.out.println("today is: ");
        System.out.println(x.format(date));
    }
}
