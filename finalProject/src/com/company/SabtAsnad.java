package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SabtAsnad {
    Scanner input = new Scanner(System.in);
    SQLConnection sqlConnection = new SQLConnection();
    private String nationalCode;

    public SabtAsnad() throws Exception {
    }

    public void login() throws SQLException {
        System.out.println("inter your national code:");
        String NCode = input.next();
        if (sqlConnection.checkNCode(NCode)) {
            String name = findName(NCode);
            System.out.println("Welcome dear " + name + ":)");
            nationalCode=NCode;
            menu();
        } else System.out.println("we not have person with this national code");
    }

    private void menu() throws SQLException {
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
        System.out.println("Document registration code  , date of buy , value");
        Estate estate = new Estate(input.next(),nationalCode, input.next(), input.nextInt());
        System.out.println("write address:");
        input.nextLine();
        String address = input.nextLine();
        estate.setAddress(address);
        System.out.println(estate.getSACode()+ " " + estate.getOwnerNCode()+ " " +estate.getAddress()+ " " +estate.getBuyDate()+" "+ estate.getValue());
        String SQLCmd = String.format("INSERT INTO Estate (SACode,ownerNCode,address,buyDate,value) VALUES ('%s','%s','%s','%s',%d)",
                estate.getSACode(), estate.getOwnerNCode(), estate.getAddress(), estate.getBuyDate(), estate.getValue());
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("estate successfully added!");
        } else System.out.println("ERROR,we have problem in estate information.please try again");

    }

    private void edit() throws SQLException {
        System.out.println("which estate information do you want to edit?inter national code and Document registration code:");
        String newNCode=input.next();
        String newSACode=input.next();

        if (sqlConnection.checkNCodeAndSACode(newNCode,newSACode)){
            System.out.println("Which filed do you want edit? ");
            System.out.println("1.Document registration code\n2.address\n3.date of buy\n4.value");
            int chooser=input.nextInt();
            switch (chooser){
                case 1: editSACode(newNCode,newSACode);
                    break;
                case 2: editAddress(newNCode,newSACode);
                    break;
                case 3: editBuyDate(newNCode,newSACode);
                    break;
                case 4: editValue(newNCode,newSACode);
            }


        }else System.out.println("Wrong nationalCode or Document registration code  :(");


    }

    private void delete() throws SQLException {
        System.out.println("Which estate do you want to delete? inter national code and Document registration code:");
        String nCode=input.next();
        String SACode=input.next();
        if (sqlConnection.checkNCodeAndSACode(nCode,SACode)){
            String SQLCmd=String.format("DELETE FROM Estate WHERE ownerNCode = '%s' AND SACode = '%s'",nCode,SACode);
            sqlConnection.executeSQL(SQLCmd);
            System.out.println("this estate was delete");
        }else System.out.println("ERROR! wrong national code or Document registration code  :(");

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

    private void editSACode(String nationalCode,String SACode){
        System.out.println("inter new Document registration code:");
        String newSACode=input.next();
        String SQLCmd=String.format("UPDATE Estate SET SACode = '%s' WHERE ownerNCode = '%s' AND SACode = '%s'",newSACode,nationalCode,SACode);
        sqlConnection.executeSQL(SQLCmd);
        System.out.println("edit completed");
    }

    private void editAddress(String nationalCode,String SACode){
        System.out.println("inter new address:");
        input.nextLine();
        String newAddress=input.nextLine();
        String SQLCmd=String.format("UPDATE Estate SET address = '%s' WHERE ownerNCode = '%s' AND SACode = '%s'",newAddress,nationalCode,SACode);
        sqlConnection.executeSQL(SQLCmd);
        System.out.println("edit completed");
    }

    private void editBuyDate(String nationalCode,String SACode){
        System.out.println("inter new date of buy (yy-mm-dd):");
        String newBuyDate=input.next();
        String SQLCmd=String.format("UPDATE Estate SET buyDate = '%s' WHERE ownerNCode = '%s' AND SACode = '%s'",newBuyDate,nationalCode,SACode);
        sqlConnection.executeSQL(SQLCmd);
        System.out.println("edit completed");
    }

    private void editValue(String nationalCode,String SACode){
        System.out.println("inter new value:");
        int newValue=input.nextInt();
        String SQLCmd=String.format("UPDATE Estate SET value = %d WHERE ownerNCode = '%s' AND SACode = '%s'",newValue,nationalCode,SACode);
        sqlConnection.executeSQL(SQLCmd);
        System.out.println("edit completed");
    }


}
