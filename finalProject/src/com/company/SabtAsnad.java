package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SabtAsnad {
    Scanner input = new Scanner(System.in);
    SQLConnection sqlConnection = new SQLConnection();

    public SabtAsnad() throws Exception {
    }

    public void login() throws SQLException {
        System.out.println("inter your national code:");
        String NCode = input.next();
        if (sqlConnection.checkNCode(NCode)) {
            String name = findName(NCode);
            System.out.println("Welcome dear " + name + ":)");
            menu();
        } else System.out.println("we not have person with this national code");
    }

    private void menu() {
        System.out.println("What do you want to do?");
        System.out.println("1.Register estate");
        System.out.println("2.Edit information of estates");
        System.out.println("3.Delete a estate");
        int chooser = input.nextInt();
        switch (chooser) {
            case 1:
                register();
                break;
            case 2:
                edit();
                break;
            case 3:
                delete();
        }
    }

    private void register() {
        System.out.println("please inter information we want:");
        System.out.println("Document registration code , owner national code , date of buy , value");
        Estate estate = new Estate(input.next(), input.next(), input.next(), input.nextInt());
        System.out.println("write address:");
        input.nextLine();
        String address = input.nextLine();
        estate.setAddress(address);
        String SQLCmd = String.format("INSERT INTO Estate (SACode,ownerNCode,address,buyDate,value) VALUES ('%s','%s','%s','%s',%d)",
                estate.getSACode(), estate.getOwnerNCode(), estate.getAddress(), estate.getBuyDate(), estate.getValue());
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("estate successfully added!");
        } else System.out.println("ERROR,we have problem in estate information.please try again");

    }

    private void edit() {

    }

    private void delete() {

    }

    private String findName(String NCode) throws SQLException {
        String name="";
        String SQlCmd = String.format("SELECT name FROM User WHERE nationalCode = %s", NCode);
        ResultSet rs = sqlConnection.SQLLoad(SQlCmd);
        while (rs.next()) {
             name = rs.getString("name");
        }
        return name;
    }
}
