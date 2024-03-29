package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class BankSystem implements Login {
    Scanner input = new Scanner(System.in);
    SQLConnection sqlConnection = new SQLConnection();
    private String nationalCode;
    private int type;

    public BankSystem() throws Exception {
    }

    @Override
    public void login() throws Exception {
        System.out.println("inter your national code:");
        String NCode = input.next();
        if (sqlConnection.checkNCode(NCode)) {
            String name = sqlConnection.findName(NCode);
            System.out.println("Welcome dear " + name + ":)");
            nationalCode = NCode;
            chooseTypeOfAccount();
            switch (type) {
                case 1:
                    CAMenu();
                    break;
                case 2:
                    GHAMenu();
                    break;
                case 3:
                    SAMenu();
                    break;
            }
        } else System.out.println("we not have person with this national code");
    }

    private void chooseTypeOfAccount() throws Exception {
        System.out.println("inter type of account:");
        System.out.println("1.current account");
        System.out.println("2.GHARZOLHASANE account");
        System.out.println("3.saving account");
        System.out.println("this item for users that have account,if you want to create account press 0 ");
        int choose = input.nextInt();
        if (choose == 0) createAccount();
        else {
            if (choose >= 1 && choose <= 3) {
                type = choose;
            } else System.out.println("Error");
        }
    }

    private void CAMenu() throws Exception {
        System.out.println("What do you want to do?");
        System.out.println("1.Push money");//واریز وجه
        System.out.println("2.pop  money");//دریافت وجه
        System.out.println("3.Get a bank card");
        System.out.println("4.Get a batch of checks");
        System.out.println("5.Pass the check");
        System.out.println("6.transfer money");
        System.out.println("7.give check");
        System.out.println("8.get vam");
        int chooser = input.nextInt();
        switch (chooser) {
            case 1:
                pushMoney();
                break;
            case 2:
                popMoney();
                break;
            case 3:
                getBankCard();
                break;
            case 4:
                getBathOfCheck();
                break;
            case 5:
                passCheck();
                break;
            case 6:
                transferMoney();
                break;
            case 7:
                giveCheck();
                break;
            case 8:
                getVam();
                break;
        }
    }

    private void GHAMenu() throws Exception {
        System.out.println("What do you want to do?");
        System.out.println("1.Push money");//واریز وجه
        System.out.println("2.pop  money");//دریافت وجه
        System.out.println("3.Get a bank card");
        System.out.println("4.transfer money");
        System.out.println("5.get vam");
        int chooser = input.nextInt();
        switch (chooser) {
            case 1:
                pushMoney();
                break;
            case 2:
                popMoney();
                break;
            case 3:
                getBankCard();
                break;
            case 4:
                transferMoney();
                break;
            case 5:
                getVam();
                break;
        }
    }

    private void SAMenu() throws Exception {
        System.out.println("What do you want to do?");
        System.out.println("1.Push money");//واریز وجه
        System.out.println("2.pop  money");//دریافت وجه
        System.out.println("3.transfer money");
        System.out.println("4.get vam");
        int chooser = input.nextInt();
        switch (chooser) {
            case 1:
                pushMoney();
                break;
            case 2:
                popMoney();
                break;
            case 3:
                transferMoney();
                break;
            case 4:
                getVam();
                break;
        }
    }

    //--------------------------------------------------------------------------------------------
    private void getVam() throws SQLException {
        System.out.println("inter your account number:");
        String ANumber = input.next();
        if (!sqlConnection.checkANumber(ANumber, type, nationalCode)) {
            System.out.println("Wrong account number");
            return;
        }
        int NPoint = sqlConnection.findNPoint(ANumber);
        if (NPoint >= 5) {
            System.out.println("your negative point mor than 5,so you are cant get vam :D");
            return;
        }
        System.out.println("What is the loan amount?");
        int value = input.nextInt();
        if (value < 0) {
            System.out.println(" :/ ");
            return;
        }
        int GHN = 0;
        int percent = 0;
        System.out.println("choose one of this options:");
        System.out.println("1.vam with 6GH and 10 interest percent ");
        System.out.println("2.vam with 12GH and 20 interest percent ");
        int chooser = input.nextInt();
        switch (chooser) {
            case 1: {
                GHN = 6;
                percent = 10;
            }
            break;
            case 2: {
                GHN = 12;
                percent = 20;
            }
            break;
        }
        if (chooser != 1 && chooser != 2) {
            System.out.println(" :/");
            return;
        }
        String SQLCmd = String.format("INSERT INTO vam (ANumber,Ncode,percent,value,GHN) VALUE ('%s','%s',%d,%d,%d)", ANumber, nationalCode, percent, value, GHN);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("vam successfully added");
        } else System.out.println("ERROR in add vam");
    }

    public void passVam() throws Exception {
        String ANumber;
        int percent;
        int value;
        int index;
        int GHN;
        String NCode;
        int wallet;
        String name;
        String SQLCmd = String.format("SELECT * FROM User INNER JOIN vam ON User.nationalCode = Vam.NCode");
        ResultSet rs = sqlConnection.SQLLoad(SQLCmd);
        while (rs.next()) {
            ANumber = rs.getString("ANumber");
            percent = rs.getInt("percent");
            value = rs.getInt("value");
            GHN = rs.getInt("GHN");
            index = rs.getInt("indexx");
            NCode = rs.getString("NCode");
            wallet = rs.getInt("walletMount");
            name = rs.getString("name");
            int payment = (value * ((percent / 100) + 1)) / GHN;

            if (wallet < payment) {
                System.out.println("person with name:" + name + " cant pass this payment of vam ");
                System.out.println("on negative point added");
                int nPoint = sqlConnection.findNPoint(ANumber) + 1;
                String SQLCmd1 = String.format("UPDATE CAccount SET NPoint = %d WHERE ANumber = '%s'", nPoint, ANumber);
                String SQLCmd2 = String.format("UPDATE GHAccount SET NPoint = %d WHERE ANumber = '%s'", nPoint, ANumber);
                String SQLCmd3 = String.format("UPDATE SAccount SET NPoint = %d WHERE ANumber = '%s'", nPoint, ANumber);
                if (sqlConnection.executeSQL(SQLCmd1) || sqlConnection.executeSQL(SQLCmd2) || sqlConnection.executeSQL(SQLCmd3)) {
                    System.out.println("negative point update");
                } else
                    System.out.println("ERROR in negative point update");
                throw new NotEnoughMoneyException();

            }
            if (index >= GHN) {
                System.out.println("The installments have already been paid");
                continue;
            }
            int newWallet = wallet - payment;
            int newIndex = index + 1;
            String SQLCmd3 = String.format("UPDATE Vam SET indexx =%d WHERE ANumber ='%s' ", newIndex, ANumber);
            String SQLCmd4 = String.format("UPDATE User SET walletMount = %d WHERE nationalCode = '%s'", newWallet, NCode);


            if (sqlConnection.executeSQLE(SQLCmd3)) {
                System.out.println("payment successfully paid ");
                System.out.println("index of payment update");
                if (sqlConnection.executeSQL(SQLCmd4)) {
                    System.out.println("wallet mount update");
                } else System.out.println("ERROR in update wallet mount");
            } else System.out.println("ERROR in payment update");


        }
    }

    //--------------------------------------------------------------------------------------------
    private void giveCheck() throws SQLException {
        System.out.println("inter your account number:");
        String DANumber = input.next();
        if (!sqlConnection.checkANumber(DANumber, 1, nationalCode)) {
            System.out.println("Wrong account number");
            return;
        }

        System.out.println("for who want to give check?inter account number and national code");
        String GANumber = input.next();
        String ownerNCode = input.next();

        if (!sqlConnection.checkANumber(GANumber, 1, ownerNCode)) {
            System.out.println("Wrong account number");
            return;
        }
        System.out.println("inter value of check");
        int value = input.nextInt();

        String SQLCmd = String.format("INSERT INTO Bcheck (DAN,ownerNCode,GAN,value) VALUES ('%s','%s','%s',%d)", DANumber, nationalCode, GANumber, value);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("check is given");
        } else System.out.println("Error in give check");
    }

    private void passCheck() throws SQLException {
        System.out.println("inter your account number");
        String GAN = input.next();
        if (!sqlConnection.checkGAN(GAN, nationalCode)) {
            System.out.println("wrong account number");
            return;
        }
        String DAN = "";
        int value = 0;
        String a = String.format("SELECT DAN , value FROM Bcheck WHERE GAN = '%s'", GAN);
        ResultSet rs = sqlConnection.SQLLoad(a);
        while (rs.next()) {
            DAN = rs.getString("DAN");
            value = rs.getInt("value");
        }
        int dBalance = sqlConnection.findBalance(DAN, 1);
        if (dBalance < value) {
            System.out.println("person who give you check dont have enough money!");
            return;
        }
        int DB = sqlConnection.findBalance(DAN, 1) - value;
        int GB = sqlConnection.findBalance(GAN, 1) + value;
        String SQLCmd1 = String.format("UPDATE CAccount SET balance = %d WHERE ANumber = '%s'", DB, DAN);
        String SQLCmd2 = String.format("UPDATE CAccount SET balance = %d WHERE ANumber = '%s'", GB, GAN);
        if (sqlConnection.executeSQL(SQLCmd1)) {
            System.out.println("given account update");
        } else System.out.println("ERROR : given account dont update");
        if (sqlConnection.executeSQL(SQLCmd2)) {
            System.out.println("getter account update");
        } else System.out.println("ERROR : getter account dont update");
        String SQLCmd3 = String.format("DELETE FROM Bcheck WHERE DAN = '%s' AND GAN = '%s'", DAN, GAN);
        if (sqlConnection.executeSQL(SQLCmd3)) {
            System.out.println("check is delete");
        } else
            System.out.println("ERROR in delete check");


    }

    //--------------------------------------------------------------------------------------------

    private void createAccount() {
        System.out.println("which type of account do yuo want to crate?");
        System.out.println("1.current account");
        System.out.println("2.GHARZOLHASANE account");
        System.out.println("3.saving account");
        int chooser = input.nextInt();
        switch (chooser) {
            case 1:
                createCAccount();
                break;
            case 2:
                createGHAccount();
                break;
            case 3: {
                try {
                    createSAccount();
                } catch (TypeInvalidException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }

    private void createCAccount() {
        System.out.println("inter information we want:");
        System.out.println("account number, balance, card number,build date");
        System.out.println("notice! account number and card number must be unique");
        String ANumber = input.next();
        int balance = input.nextInt();
        String cardNumber = input.next();
        String buildDate = input.next();
        CurrentBankAccount CAccount = new CurrentBankAccount(ANumber, nationalCode, balance, buildDate, cardNumber);
        String SQlCmd = String.format("INSERT INTO CAccount (ANumber,ownerNCode,balance,buildDate,NPoint,checkBook,bankCard,cardNumber) VALUE ('%s','%s',%d,'%s',%d,%d,%d,'%s')",
                CAccount.getAccountNumber(), CAccount.getOwnerNCode(), CAccount.getBalance(), CAccount.getBuildDate(), CAccount.getNegativePoint(), CAccount.getCheckbook(), CAccount.getBankCard(), CAccount.getCardNumber());
        if (sqlConnection.executeSQL(SQlCmd)) {
            System.out.println("your current account created");
        } else System.out.println("ERROR!");
    }

    private void createGHAccount() {
        System.out.println("inter information we want:");
        System.out.println("account number, balance, card number,build date");
        System.out.println("notice! account number and card number must be unique");
        String ANumber = input.next();
        int balance = input.nextInt();
        String cardNumber = input.next();
        String buildDate = input.next();
        GhHBankAccount ghHBankAccount = new GhHBankAccount(ANumber, nationalCode, balance, buildDate, cardNumber);
        String SQlCmd = String.format("INSERT INTO GHAccount (ANumber,ownerNCode,balance,buildDate,NPoint,bankCard,cardNumber) VALUE ('%s','%s',%d,'%s',%d,%d,'%s')",
                ghHBankAccount.getAccountNumber(), ghHBankAccount.getOwnerNCode(), ghHBankAccount.getBalance(), ghHBankAccount.getBuildDate(), ghHBankAccount.getNegativePoint(), ghHBankAccount.getBankCard(), ghHBankAccount.getCardNumber());
        if (sqlConnection.executeSQL(SQlCmd)) {
            System.out.println("your gharzolhasane account created");
        } else System.out.println("ERROR!");

    }

    private void createSAccount() throws TypeInvalidException {
        Date date = new Date();
        SimpleDateFormat x = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = x.format(date);
        System.out.println("inter information we want:");
        System.out.println("account number, balance");
        System.out.println("notice! account number and card number must be unique");
        String ANumber = input.next();
        int balance = input.nextInt();
        String buildDate = currentTime;
        System.out.println("inter type of saving account:");
        System.out.println("1.SPECIAL");
        System.out.println("2.short term");
        System.out.println("3.long term");
        int chooser = input.nextInt();

        if (chooser < 1 || chooser > 3) {
            throw new TypeInvalidException();
        }


        SavingBankAccount savingBankAccount = new SavingBankAccount(ANumber, nationalCode, balance, buildDate);
        switch (chooser) {
            case 1:
                savingBankAccount.setType(SavingBankAccount.type.SPECIAL);
                break;
            case 2:
                savingBankAccount.setType(SavingBankAccount.type.SHORT_TERM);
                break;
            case 3:
                savingBankAccount.setType(SavingBankAccount.type.LONG_TERM);
                break;
        }
        savingBankAccount.setInterestAndPeriod();
        String SQlCmd = String.format("INSERT INTO SAccount (ANumber,ownerNCode,balance,buildDate,NPoint,type,bankInterest,period) VALUE ('%s','%s',%d,'%s',%d,'%s',%d,%d)",
                savingBankAccount.getAccountNumber(), savingBankAccount.getOwnerNCode(), savingBankAccount.getBalance(), savingBankAccount.getBuildDate(), savingBankAccount.getNegativePoint(), savingBankAccount.getType(), savingBankAccount.getBankInterest(), savingBankAccount.getPeriod());
        if (sqlConnection.executeSQL(SQlCmd)) {
            System.out.println("your saving account created");
        } else System.out.println("ERROR!");
    }
    //----------------------------------------------------------------------------------------------

    private void pushMoney() throws Exception {

        System.out.println("inter your account number:");
        String ANumber = input.next();
        if (sqlConnection.checkANumber(ANumber, type, nationalCode)) {
            System.out.println("How much money do you want to deposit?");
            int money = input.nextInt();
            System.out.println(sqlConnection.getWallet(nationalCode));
            if (sqlConnection.getWallet(nationalCode) >= money) {

                switch (type) {
                    case 1:
                        pushMoneyForCA(ANumber, money);
                        break;
                    case 2:
                        pushMoneyForGHA(ANumber, money);
                        break;
                    case 3:
                        pushMoneyForSA(ANumber, money);
                        break;
                }
            } else System.out.println("you are not have this amount in your wallet !");
        } else
            System.out.println("wrong account number :(");

    }

    private void pushMoneyForCA(String ANumber, int money) throws Exception {
        int balance = sqlConnection.findBalance(ANumber, 1) + money;
        String SQLCmd = String.format("UPDATE CAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            popToWallet(money);
            System.out.println("push money is complete");
        } else System.out.println("ERROR : push money is not complete");
    }

    private void pushMoneyForGHA(String ANumber, int money) throws Exception {
        int balance = sqlConnection.findBalance(ANumber, 2) + money;
        String SQLCmd = String.format("UPDATE GHAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            popToWallet(money);
            System.out.println("push money is complete");
        } else System.out.println("ERROR : push money is not complete");
    }

    private void pushMoneyForSA(String ANumber, int money) throws Exception {
        int balance = sqlConnection.findBalance(ANumber, 3) + money;
        String SQLCmd = String.format("UPDATE SAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            popToWallet(money);
            System.out.println("push money is complete");
        } else System.out.println("ERROR : push money is not complete");
    }

    private void popToWallet(int money) throws Exception {
        int walletMount = sqlConnection.getWallet(nationalCode) - money;
        String SQLCmd = String.format("UPDATE User SET walletMount = %d WHERE nationalCode = '%s'", walletMount, nationalCode);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("wallet mount update");
        } else System.out.println("ERROR : in transfer money to wallet ");
    }
    //------------------------------------------------------------------------------------------------

    private void popMoney() throws Exception {

        System.out.println("inter your account number:");
        String ANumber = input.next();
        if (sqlConnection.checkANumber(ANumber, type, nationalCode)) {
            System.out.println("How much money do you want to withdraw?");
            int money = input.nextInt();
            if (money <= sqlConnection.findBalance(ANumber, type)) {
                switch (type) {
                    case 1:
                        popMoneyForCA(ANumber, money);
                        break;
                    case 2:
                        popMoneyForGHA(ANumber, money);
                        break;
                    case 3:
                        popMoneyForSA(ANumber, money);
                        break;
                }
            } else System.out.println("Your balance is not enough");
        } else
            System.out.println("wrong account number :(");
    }

    private void popMoneyForCA(String ANumber, int money) throws Exception {
        int balance = sqlConnection.findBalance(ANumber, 1) - money;
        pushToWallet(money);
        String SQLCmd = String.format("UPDATE CAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("pop money is complete");
        } else System.out.println("ERROR : pup money is not complete");
    }

    private void popMoneyForGHA(String ANumber, int money) throws Exception {
        int balance = sqlConnection.findBalance(ANumber, 2) - money;
        pushToWallet(money);
        String SQLCmd = String.format("UPDATE GHAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("pop money is complete");
        } else System.out.println("ERROR : pup money is not complete");
    }

    private void popMoneyForSA(String ANumber, int money) throws Exception {



        int balance = sqlConnection.findBalance(ANumber, 3) - money;
        pushToWallet(money);
        String SQLCmd1 = String.format("UPDATE SAccount SET balance = %d WHERE ANumber = '%s'", balance, ANumber);
        if (sqlConnection.executeSQL(SQLCmd1)) {
            System.out.println("pop money is complete");
        } else System.out.println("ERROR : pup money is not complete");
    }




    private void pushToWallet(int money) throws Exception {
        int walletMount = sqlConnection.getWallet(nationalCode) + money;
        String SQLCmd = String.format("UPDATE User SET walletMount = %d WHERE nationalCode = '%s'", walletMount, nationalCode);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("wallet mount update");
        } else System.out.println("ERROR : in transfer money to wallet ");
    }

    //------------------------------------------------------------------------------------------------


    private void getBankCard() throws SQLException {

        System.out.println("inter your account number:");
        String ANumber = input.next();
        if (sqlConnection.checkANumber(ANumber, type, nationalCode)) {
            if (!checkGetBK(ANumber, type)) {
                switch (type) {
                    case 1:
                        GBCForCA(ANumber);
                        break;
                    case 2:
                        GBCForGHA(ANumber);
                        break;
                    case 3:
                        GBCForSA(ANumber);
                }
            } else System.out.println("you already get bank card ");
        } else
            System.out.println("wrong account number :(");
    }

    private void GBCForCA(String ANumber) {
        String SQLCmd = String.format("UPDATE CAccount SET bankCard = %d WHERE ANumber = '%s'", 0, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("bank card is given");
        } else System.out.println("ERROR : in get bank card");
    }

    private void GBCForGHA(String ANumber) {
        String SQLCmd = String.format("UPDATE GHAccount SET bankCard = %d WHERE ANumber = '%s'", 0, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("bank card is given");
        } else System.out.println("ERROR : in get bank card");
    }

    private void GBCForSA(String ANumber) {
        String SQLCmd = String.format("UPDATE SAccount SET bankCard = %d WHERE ANumber = '%s'", 0, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("bank card is given");
        } else System.out.println("ERROR : in get bank card");
    }

    private boolean checkGetBK(String ANumber, int type) throws SQLException {
        boolean check = false;
        if (type == 1) {
            String SQLCmd = String.format("SELECT bankCard FROM CAccount WHERE ANumber = '%s'", ANumber);
            ResultSet rs = sqlConnection.SQLLoad(SQLCmd);
            while (rs.next()) {
                if (rs.getInt("bankCard") == 0) {
                    check = true;
                    break;
                }
            }
        }

        if (type == 2) {
            String SQLCmd = String.format("SELECT bankCard FROM GHAccount WHERE ANumber = '%s'", ANumber);
            ResultSet rs = sqlConnection.SQLLoad(SQLCmd);
            while (rs.next()) {
                if (rs.getInt("bankCard") == 0) {
                    check = true;
                    break;
                }
            }
        }

        if (type == 3) {
            String SQLCmd = String.format("SELECT bankCard FROM SAccount WHERE ANumber = '%s'", ANumber);
            ResultSet rs = sqlConnection.SQLLoad(SQLCmd);
            while (rs.next()) {
                if (rs.getInt("bankCard") == 0) {
                    check = true;
                    break;
                }
            }
        }

        return check;
    }

    //------------------------------------------------------------------------------------------------------

    private void getBathOfCheck() throws SQLException {
        System.out.println("inter your account number:");
        String ANumber = input.next();
        if (sqlConnection.checkANumber(ANumber, type, nationalCode)) {
            if (!checkGetCHB(ANumber, type)) {
                switch (type) {
                    case 1:
                        GChBForCA(ANumber);
                        break;
                    case 2:
                        GChBForGHA(ANumber);
                        break;
                    case 3:
                        GChBForSA(ANumber);
                }
            } else System.out.println("you already get check book ");
        } else
            System.out.println("wrong account number :(");
    }

    private void GChBForCA(String ANumber) {
        String SQLCmd = String.format("UPDATE CAccount SET checkBook = %d WHERE ANumber = '%s'", 0, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("check book is given");
        } else System.out.println("ERROR : in get check book");
    }

    private void GChBForGHA(String ANumber) {
        String SQLCmd = String.format("UPDATE GHAccount SET checkBook = %d WHERE ANumber = '%s'", 0, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("check book is given");
        } else System.out.println("ERROR : in get check book");
    }

    private void GChBForSA(String ANumber) {
        String SQLCmd = String.format("UPDATE SAccount SET checkBook = %d WHERE ANumber = '%s'", 0, ANumber);
        if (sqlConnection.executeSQL(SQLCmd)) {
            System.out.println("check book is given");
        } else System.out.println("ERROR : in get check book");
    }

    private boolean checkGetCHB(String ANumber, int type) throws SQLException {
        boolean check = false;
        if (type == 1) {
            String SQLCmd = String.format("SELECT checkBook FROM CAccount WHERE ANumber = '%s'", ANumber);
            ResultSet rs = sqlConnection.SQLLoad(SQLCmd);
            while (rs.next()) {
                if (rs.getInt("checkBook") == 0) {
                    check = true;
                    break;
                }
            }
        }

        if (type == 2) {
            String SQLCmd = String.format("SELECT checkBook FROM GHAccount WHERE ANumber = '%s'", ANumber);
            ResultSet rs = sqlConnection.SQLLoad(SQLCmd);
            while (rs.next()) {
                if (rs.getInt("checkBook") == 0) {
                    check = true;
                    break;
                }
            }
        }

        if (type == 3) {
            String SQLCmd = String.format("SELECT checkBook FROM SAccount WHERE ANumber = '%s'", ANumber);
            ResultSet rs = sqlConnection.SQLLoad(SQLCmd);
            while (rs.next()) {
                if (rs.getInt("checkBook") == 0) {
                    check = true;
                    break;
                }
            }
        }

        return check;
    }

    //--------------------------------------------------------------------------------------------


    //---------------------------------------------------------------------------------------------------
    private void transferMoney() throws SQLException {
        System.out.println("inter your  origin account number:");
        String OANumber = input.next();
        if (!sqlConnection.checkANumber(OANumber, type, nationalCode)) {
            System.out.println("Wrong account number");
            return;
        }

        System.out.println("inter money you want to transfer:");
        int money = input.nextInt();
        if (sqlConnection.findBalance(OANumber, type) < money) {
            System.out.println("origin account not have enough money!");
            return;
        }

        System.out.println("remember you just can transfer money on same type account :)");
        System.out.println("inter your  target account number:");
        String TANumber = input.next();
        System.out.println("inter national code of person who want to transfer money");
        String TNationalCode = input.next();
        if (!sqlConnection.checkANumber(TANumber, type, TNationalCode)) {
            System.out.println("Wrong account number");
            return;
        }

        switch (type) {
            case 1:
                TMForCA(OANumber, TANumber, money);
                break;
            case 2:
                TMForGHA(OANumber, TANumber, money);
                break;
            case 3:
                TMForSA(OANumber, TANumber, money);
                break;
        }


    }

    private void TMForCA(String OANumber, String TANumber, int money) throws SQLException {
        int oBalance = sqlConnection.findBalance(OANumber, type) - money;
        String SQLCmd1 = String.format("UPDATE CAccount SET balance = %d WHERE ANumber = '%s'", oBalance, OANumber);
        if (sqlConnection.executeSQL(SQLCmd1)) {
            System.out.println("origin balance account update");
        } else System.out.println("ERROR : origin balance account not update");
        int tBalance = sqlConnection.findBalance(TANumber, type) + money;
        String SQLCmd2 = String.format("UPDATE CAccount SET balance = %d WHERE ANumber = '%s'", tBalance, TANumber);
        if (sqlConnection.executeSQL(SQLCmd2)) {
            System.out.println("target balance account update");
        } else System.out.println("ERROR : target balance account not update");

    }

    private void TMForGHA(String OANumber, String TANumber, int money) throws SQLException {
        int oBalance = sqlConnection.findBalance(OANumber, type) - money;
        String SQLCmd1 = String.format("UPDATE GHAccount SET balance = %d WHERE ANumber = '%s'", oBalance, OANumber);
        if (sqlConnection.executeSQL(SQLCmd1)) {
            System.out.println("origin balance account update");
        } else System.out.println("ERROR : origin balance account not update");
        int tBalance = sqlConnection.findBalance(TANumber, type) + money;
        String SQLCmd2 = String.format("UPDATE GHAccount SET balance = %d WHERE ANumber = '%s'", tBalance, TANumber);
        if (sqlConnection.executeSQL(SQLCmd2)) {
            System.out.println("target balance account update");
        } else System.out.println("ERROR : target balance account not update");
    }

    private void TMForSA(String OANumber, String TANumber, int money) throws SQLException {
        int oBalance = sqlConnection.findBalance(OANumber, type) - money;
        String SQLCmd1 = String.format("UPDATE SAccount SET balance = %d WHERE ANumber = '%s'", oBalance, OANumber);
        if (sqlConnection.executeSQL(SQLCmd1)) {
            System.out.println("origin balance account update");
        } else System.out.println("ERROR : origin balance account not update");
        int tBalance = sqlConnection.findBalance(TANumber, type) + money;
        String SQLCmd2 = String.format("UPDATE SAccount SET balance = %d WHERE ANumber = '%s'", tBalance, TANumber);
        if (sqlConnection.executeSQL(SQLCmd2)) {
            System.out.println("target balance account update");
        } else System.out.println("ERROR : target balance account not update");
    }

}
