package com.company;


import java.util.Scanner;

public class SabtAhval {
    Scanner input=new Scanner(System.in);
    SQLConnection sqlConnection=new SQLConnection();


    public SabtAhval() throws Exception {
    }

    public void menu()  {
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

    private void editInformation()  {
        System.out.println("which user information do you want to edit?inter national code:");
        String newNCode=input.next();





    }

    private void removeInformation(){

    }
}
