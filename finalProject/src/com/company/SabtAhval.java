package com.company;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SabtAhval {
    Scanner input=new Scanner(System.in);
    SQLConnection sqlConnection=new SQLConnection();


    public SabtAhval() throws Exception {
    }

    public void menu() throws SQLException {
        System.out.println("What do you want to do Boss?");
        System.out.println("1.Register user");
        System.out.println("2.Editing information");
        System.out.println("3.Remove information");
        int chooser=input.nextInt();
        switch (chooser){
            case 1: addUser();
            break;
            case 2: editInformation();
            break;
            case 3: removeInformation();
        }
    }

    private void addUser()  {
        System.out.println("please inter information we want:");
        System.out.println("name , national code , age , wallet mount");
        User user=new User(input.next(),input.next(),input.nextInt(),input.nextInt());
        System.out.println("select gender:1.male\t2.female");
        int chooser=input.nextInt();
        switch (chooser){
            case 1: user.setGender(User.gender.MALE);
            break;
            case 2: user.setGender(User.gender.FEMALE);
            break;
        }
        String SQLCmd= String.format("INSERT INTO User (name,nationalCode,age,gender,walletMount) VALUES ('%s','%s',%d,'%s',%d)",
                user.getName(),user.getNationalCode(),user.getAge(),user.getGender(),user.getWallet());
        if (sqlConnection.executeSQL(SQLCmd)){
            System.out.println("person successfully added!");
        }else System.out.println("ERROR,we have problem in user information.please try again");



    }

    private void editInformation() throws SQLException {

        System.out.println("which user information do you want to edit?inter national code:");
        String newNCode=input.next();

        if (sqlConnection.checkNCode(newNCode)){
            System.out.println("Which filed do you want edit? ");
            System.out.println("1.name\n2.age\n3.gender\n4.wallet mount");
            int chooser=input.nextInt();
            switch (chooser){
                case 1: editName(newNCode);
                break;
                case 2: editAge(newNCode);
                break;
                case 3:editGender(newNCode);
                break;
                case 4:editWalletMount(newNCode);
            }


        }else System.out.println("Wrong nationalCode :(");


    }

    private void removeInformation() throws SQLException {
        System.out.println("Which user do you want to delete? inter national code:");
        String nCode=input.next();
        if (sqlConnection.checkNCode(nCode)){
            String SQLCmd=String.format("DELETE FROM User WHERE nationalCode = '%s'",nCode);
            sqlConnection.executeSQL(SQLCmd);
        }else System.out.println("ERROR! wrong national code");


    }

    private void editName(String nationalCode){
        System.out.println("inter new name:");
        String newName=input.next();
        String SQLCmd=String.format("UPDATE User SET name = '%s' WHERE nationalCode = '%s'",newName,nationalCode);
        sqlConnection.executeSQL(SQLCmd);
        System.out.println("edit completed");
    }

    private void editAge (String nationalCode){
        System.out.println("inter new age:");
        int newAge=input.nextInt();
        String SQLCmd=String.format("UPDATE User SET age = '%d' WHERE nationalCode = '%s'",newAge,nationalCode);
        sqlConnection.executeSQL(SQLCmd);
        System.out.println("edit completed");
    }

    private void editGender(String nationalCode){
        System.out.println("select gender:\t1.male\t2.female");
        int chooser=input.nextInt();
        String newGender;
        if (chooser==1) newGender="MALE";
        else newGender="FEMALE";
        String SQLCmd=String.format("UPDATE User SET gender = '%s' WHERE nationalCode = '%s'",newGender,nationalCode);
        sqlConnection.executeSQL(SQLCmd);
        System.out.println("edit completed");
    }

    private void editWalletMount(String nationalCode){
        System.out.println("inter new wallet mount:");
        int newWM=input.nextInt();
        String SQLCmd=String.format("UPDATE User SET walletMount = '%d' WHERE nationalCode = '%s'",newWM,nationalCode);
        sqlConnection.executeSQL(SQLCmd);
        System.out.println("edit completed");
    }


}
